package com.proyectoraul.demo.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.proyectoraul.demo.auth.TokenService;
import com.proyectoraul.demo.dto.DatosToken;
import com.proyectoraul.demo.dto.TareasDTO;
import com.proyectoraul.demo.service.TareasService;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class TareasControllerTest {

    @Test
    void crearObtieneIdDelCreadorDesdeElToken() {
        TareasService tareasService = mock(TareasService.class);
        TokenService tokenService = mock(TokenService.class);
        TareasController controller = new TareasController(tareasService, tokenService);
        TareasDTO dto = new TareasDTO();
        Jwt jwt = Jwt.withTokenValue("token-validado")
                .header("alg", "HS256")
                .subject("usuario")
                .build();

        when(tokenService.obtenerDatosToken("token-validado"))
                .thenReturn(new DatosToken(7, "usuario", "usuario@example.com", "Usuario"));

        controller.create(dto, jwt);

        verify(tareasService).create(dto, 7);
    }
}
