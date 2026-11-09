package com.proyectoraul.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyectoraul.demo.auth.TokenService;
import com.proyectoraul.demo.dto.DatosToken;
import com.proyectoraul.demo.dto.PisoDTO;
import com.proyectoraul.demo.service.PisoService;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class PisoControllerTest {

    @Test
    void crearObtieneIdUsuarioDelTokenYNoDelRequest() throws Exception {
        PisoService pisoService = mock(PisoService.class);
        TokenService tokenService = mock(TokenService.class);
        PisoController controller = new PisoController(pisoService, tokenService);
        PisoDTO dto = new PisoDTO();
        dto.setNombre("Piso de prueba");
        dto.setIdUsuario(99);
        Jwt jwt = Jwt.withTokenValue("token-validado")
                .header("alg", "HS256")
                .subject("usuario")
                .build();

        assertEquals(JsonProperty.Access.READ_ONLY,
                PisoDTO.class.getDeclaredField("idUsuario")
                        .getAnnotation(JsonProperty.class).access());
        when(tokenService.obtenerDatosToken("token-validado"))
                .thenReturn(new DatosToken(7, "usuario", "usuario@example.com", "Usuario"));

        controller.crear(dto, jwt);

        verify(pisoService).crear(dto, 7);
    }
}
