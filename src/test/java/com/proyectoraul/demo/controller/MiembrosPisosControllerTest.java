package com.proyectoraul.demo.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.proyectoraul.demo.auth.TokenService;
import com.proyectoraul.demo.dto.CrearMiembroPisoDTO;
import com.proyectoraul.demo.dto.DatosToken;
import com.proyectoraul.demo.model.Role;
import com.proyectoraul.demo.service.MiembrosPisosService;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class MiembrosPisosControllerTest {

    @Test
    void crearUsaElUsuarioDelTokenYElCodigoDelPiso() {
        MiembrosPisosService miembrosPisosService = mock(MiembrosPisosService.class);
        TokenService tokenService = mock(TokenService.class);
        MiembrosPisosController controller = new MiembrosPisosController(
                miembrosPisosService, tokenService);
        CrearMiembroPisoDTO dto = new CrearMiembroPisoDTO();
        dto.setCodigoInvitacion("codigo-piso");
        dto.setRol(Role.MIEMBRO);
        Jwt jwt = Jwt.withTokenValue("token-validado")
                .header("alg", "HS256")
                .subject("usuario")
                .build();

        when(tokenService.obtenerDatosToken("token-validado"))
                .thenReturn(new DatosToken(7, "usuario", "usuario@example.com", "Usuario"));

        controller.create(dto, jwt);

        verify(miembrosPisosService).create(7, "codigo-piso", Role.MIEMBRO);
    }
}
