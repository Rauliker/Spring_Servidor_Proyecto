package com.proyectoraul.demo.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

class SecurityConfigTest {

    private final BearerTokenResolver bearerTokenResolver = new SecurityConfig().bearerTokenResolver();
    private final CorsConfigurationSource corsConfigurationSource = new SecurityConfig()
            .corsConfigurationSource();

    @Test
    void postDeRegistroIgnoraToken() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/usuarios");
        request.addHeader("Authorization", "Bearer invalido");

        assertNull(bearerTokenResolver.resolve(request));
    }

    @Test
    void postDeRegistroSinPrefijoApiIgnoraToken() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/usuarios");
        request.addHeader("Authorization", "Bearer invalido");

        assertNull(bearerTokenResolver.resolve(request));
    }

    @Test
    void getDeUsuariosIgnoraToken() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/usuarios");
        request.addHeader("Authorization", "Bearer invalido");

        assertNull(bearerTokenResolver.resolve(request));
    }

    @Test
    void getDeUsuarioPorIdIgnoraToken() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/usuarios/7");
        request.addHeader("Authorization", "Bearer invalido");

        assertNull(bearerTokenResolver.resolve(request));
    }

    @Test
    void getPublicoSinPrefijoApiIgnoraToken() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/usuarios");
        request.addHeader("Authorization", "Bearer invalido");

        assertNull(bearerTokenResolver.resolve(request));
    }

    @Test
    void getEnOtraRutaSigueProcesandoElToken() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/tareas");
        request.addHeader("Authorization", "Bearer token-get");

        assertEquals("token-get", bearerTokenResolver.resolve(request));
    }

    @Test
    void postEnOtraRutaSigueProcesandoElToken() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/tareas");
        request.addHeader("Authorization", "Bearer token-otra-ruta");

        assertEquals("token-otra-ruta", bearerTokenResolver.resolve(request));
    }

    @Test
    void preflightDeAngularPermitePostConBearerToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/usuarios");
        request.addHeader("Origin", "http://localhost:4200");
        request.addHeader("Access-Control-Request-Method", "POST");
        request.addHeader("Access-Control-Request-Headers", "authorization,content-type");
        MockHttpServletResponse response = new MockHttpServletResponse();

        new CorsFilter(corsConfigurationSource).doFilter(request, response, (req, res) -> {
                throw new AssertionError("La preflight debería terminar en el filtro CORS");
        });

        assertEquals(200, response.getStatus());
        assertEquals("http://localhost:4200", response.getHeader("Access-Control-Allow-Origin"));
        assertNotNull(response.getHeader("Access-Control-Allow-Methods"));
        assertNotNull(response.getHeader("Access-Control-Allow-Headers"));
    }

    @Test
    void noPermiteCorsDesdeOtrosOrigenes() {
        CorsConfiguration configuration = corsConfigurationSource.getCorsConfiguration(
                new MockHttpServletRequest("GET", "/api/usuarios"));

        assertNotNull(configuration);
        assertNull(configuration.checkOrigin("http://localhost:4201"));
    }
}
