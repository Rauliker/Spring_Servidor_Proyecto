package com.proyectoraul.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.proyectoraul.demo.dto.UsuarioDTO;
import com.proyectoraul.demo.dto.UsuarioPublicoDTO;
import com.proyectoraul.demo.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class UsuarioControllerTest {

    @Test
    void listarDevuelveSoloDatosPublicos() {
        UsuarioService service = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(service);
        UsuarioDTO usuario = new UsuarioDTO(7, "Usuario", "user", "usuario@example.com", "hash");
        when(service.listar()).thenReturn(List.of(usuario));

        List<UsuarioPublicoDTO> resultado = controller.listar();

        assertEquals(List.of(new UsuarioPublicoDTO(7, "Usuario", "user")), resultado);
    }

    @Test
    void buscarPorIdDevuelveSoloDatosPublicos() {
        UsuarioService service = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(service);
        UsuarioDTO usuario = new UsuarioDTO(7, "Usuario", "user", "usuario@example.com", "hash");
        when(service.buscarPorIdUsuario(7)).thenReturn(usuario);

        UsuarioPublicoDTO resultado = controller.buscarPorIdUsuario(7);

        assertEquals(new UsuarioPublicoDTO(7, "Usuario", "user"), resultado);
    }

    @Test
    void postDeRegistroAceptaAmbasRutas() throws Exception {
        UsuarioService service = mock(UsuarioService.class);
        when(service.crear(any(UsuarioDTO.class)))
                .thenReturn(new UsuarioDTO(7, "Usuario", "user", "usuario@example.com", "hash"));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UsuarioController(service)).build();
        String requestBody = """
                {"nombre":"Usuario","userName":"user","email":"usuario@example.com","password":"clave-segura"}
                """;

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }
}
