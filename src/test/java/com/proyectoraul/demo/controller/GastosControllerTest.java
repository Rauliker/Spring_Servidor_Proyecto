package com.proyectoraul.demo.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.proyectoraul.demo.auth.TokenService;
import com.proyectoraul.demo.dto.DatosToken;
import com.proyectoraul.demo.dto.GastosDTO;
import com.proyectoraul.demo.service.GastosService;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class GastosControllerTest {

    @Test
    void crearObtieneIdDelCreadorDesdeElToken() {
        GastosService gastosService = mock(GastosService.class);
        TokenService tokenService = mock(TokenService.class);
        GastosController controller = new GastosController(gastosService, tokenService);
        GastosDTO dto = new GastosDTO();
        Jwt jwt = Jwt.withTokenValue("token-validado")
                .header("alg", "HS256")
                .subject("usuario")
                .build();

        when(tokenService.obtenerDatosToken("token-validado"))
                .thenReturn(new DatosToken(7, "usuario", "usuario@example.com", "Usuario"));

        controller.create(dto, jwt);

        verify(gastosService).create(dto, 7);
    }

    @Test
    void consultarGastosPorPisoAceptaLaRutaSinPrefijoApi() throws Exception {
        GastosService gastosService = mock(GastosService.class);
        when(gastosService.findByPiso(42)).thenReturn(List.of(new GastosDTO()));
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new GastosController(gastosService, mock(TokenService.class)))
                .build();

        mockMvc.perform(get("/gastos/piso/42"))
                .andExpect(status().isOk());

        verify(gastosService).findByPiso(42);
    }
}
