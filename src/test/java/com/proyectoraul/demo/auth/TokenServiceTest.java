package com.proyectoraul.demo.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.proyectoraul.demo.dto.DatosToken;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

class TokenServiceTest {

    @Test
    void obtenerDatosTokenConvierteIdUsuarioCuandoElClaimVieneComoLong() {
        JwtEncoder jwtEncoder = mock(JwtEncoder.class);
        JwtDecoder jwtDecoder = mock(JwtDecoder.class);
        TokenService tokenService = new TokenService(jwtEncoder, jwtDecoder);

        Jwt jwt = Jwt.withTokenValue("token-validado")
                .header("alg", "HS256")
                .claim("idUsuario", 7L)
                .claim("userName", "usuario")
                .claim("email", "usuario@example.com")
                .claim("nombre", "Usuario")
                .build();

        when(jwtDecoder.decode("token-validado")).thenReturn(jwt);

        DatosToken datos = tokenService.obtenerDatosToken("token-validado");

        assertEquals(Integer.valueOf(7), datos.idUsuario());
        assertEquals("usuario", datos.userName());
        assertEquals("usuario@example.com", datos.email());
        assertEquals("Usuario", datos.nombre());
    }
}
