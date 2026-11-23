
package com.proyectoraul.demo.config;

import java.util.Base64;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {
        private static final String USUARIOS_PATH = "/api/usuarios";
        private static final String USUARIOS_ALIAS = "/usuarios";

        /**
         * Lee la clave JWT en Base64 y crea la clave HMAC.
         */
        @Bean
        SecretKey jwtSecretKey(
                        @Value("${jwt.secret}") String secret) {

                final byte[] keyBytes;

                try {
                        keyBytes = Base64.getDecoder().decode(secret.trim());
                } catch (IllegalArgumentException ex) {
                        throw new IllegalStateException(
                                        "JWT_SECRET_BASE64 no contiene una clave Base64 válida.",
                                        ex);
                }

                if (keyBytes.length < 32) {
                        throw new IllegalStateException(
                                        "La clave JWT debe tener al menos 32 bytes para HS256.");
                }

                return new SecretKeySpec(keyBytes, "HmacSHA256");
        }

        /**
         * Firma los tokens JWT utilizando HS256.
         */
        @Bean
        JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {

                var jwk = new OctetSequenceKey.Builder(jwtSecretKey)
                                .keyUse(KeyUse.SIGNATURE)
                                .algorithm(JWSAlgorithm.HS256)
                                .build();

                var jwkSource = new ImmutableJWKSet<SecurityContext>(
                                new JWKSet(jwk));

                return new NimbusJwtEncoder(jwkSource);
        }

        /**
         * Valida la firma de los tokens JWT recibidos.
         */
        @Bean
        JwtDecoder jwtDecoder(SecretKey jwtSecretKey) {

                return NimbusJwtDecoder
                                .withSecretKey(jwtSecretKey)
                                .macAlgorithm(MacAlgorithm.HS256)
                                .build();
        }

        /**
         * Codifica las contraseñas y permite comprobarlas durante el login.
         */
        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        BearerTokenResolver bearerTokenResolver() {
                BearerTokenResolver defaultResolver = new DefaultBearerTokenResolver();

                return request -> {
                        String path = request.getRequestURI().substring(request.getContextPath().length());
                        boolean postRegistro = HttpMethod.POST.matches(request.getMethod())
                                        && (USUARIOS_PATH.equals(path) || USUARIOS_ALIAS.equals(path));
                        boolean getUsuario = HttpMethod.GET.matches(request.getMethod())
                                        && (esRutaDeUsuario(path, USUARIOS_PATH)
                                                        || esRutaDeUsuario(path, USUARIOS_ALIAS));

                        if (postRegistro || getUsuario) {
                                return null;
                        }

                        return defaultResolver.resolve(request);
                };
        }

        /**
         * Configura las rutas públicas y protegidas.
         */
        @Bean
        SecurityFilterChain securityFilterChain(
                        HttpSecurity http,
                        BearerTokenResolver bearerTokenResolver) throws Exception {

                http
                                .cors(Customizer.withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/login").permitAll()
                                                .requestMatchers(HttpMethod.POST, USUARIOS_PATH, USUARIOS_ALIAS).permitAll()
                                                .requestMatchers(HttpMethod.GET,
                                                                USUARIOS_PATH, USUARIOS_PATH + "/*",
                                                                USUARIOS_ALIAS, USUARIOS_ALIAS + "/*")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .bearerTokenResolver(bearerTokenResolver)
                                                .jwt(Customizer.withDefaults()));

                return http.build();
        }

        private static boolean esRutaDeUsuario(String path, String basePath) {
                if (basePath.equals(path)) {
                        return true;
                }

                String prefix = basePath + "/";
                return path.startsWith(prefix)
                                && path.length() > prefix.length()
                                && path.indexOf('/', prefix.length()) == -1;
        }
}
