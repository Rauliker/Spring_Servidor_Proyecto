package com.proyectoraul.demo.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.proyectoraul.demo.dto.DatosToken;

@Service
public class TokenService {

        private final JwtEncoder jwtEncoder;
        private final JwtDecoder jwtDecoder;

        public TokenService(
                        JwtEncoder jwtEncoder,
                        JwtDecoder jwtDecoder) {
                this.jwtEncoder = jwtEncoder;
                this.jwtDecoder = jwtDecoder;
        }

        public String generarToken(
                        Integer idUsuario,
                        String userName,
                        String email,
                        String nombre) {

                Instant ahora = Instant.now();

                JwtClaimsSet claims = JwtClaimsSet.builder()
                                .issuer("demo-api")
                                .subject(userName)
                                .claim("idUsuario", idUsuario)
                                .claim("userName", userName)
                                .claim("email", email)
                                .claim("nombre", nombre)
                                .issuedAt(ahora)
                                .expiresAt(ahora.plus(30, ChronoUnit.MINUTES))
                                .build();

                JwsHeader header = JwsHeader
                                .with(MacAlgorithm.HS256)
                                .build();

                return jwtEncoder.encode(
                                JwtEncoderParameters.from(header, claims)).getTokenValue();
        }

        public DatosToken obtenerDatosToken(String token) {

                Jwt jwt = jwtDecoder.decode(token);

                return new DatosToken(
                                claimAsInteger(jwt, "idUsuario"),
                                claimAsString(jwt, "userName"),
                                claimAsString(jwt, "email"),
                                claimAsString(jwt, "nombre"));
        }

        private Integer claimAsInteger(Jwt jwt, String claimName) {
                Object claimValue = jwt.getClaim(claimName);

                if (claimValue == null) {
                        return null;
                }

                if (claimValue instanceof Number number) {
                        return number.intValue();
                }

                if (claimValue instanceof String stringValue) {
                        return Integer.valueOf(stringValue);
                }

                throw new IllegalArgumentException(
                                "El claim '" + claimName + "' debe ser numérico: " + claimValue);
        }

        private String claimAsString(Jwt jwt, String claimName) {
                Object claimValue = jwt.getClaim(claimName);
                return claimValue == null ? null : String.valueOf(claimValue);
        }
}