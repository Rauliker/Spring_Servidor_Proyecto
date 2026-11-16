package com.proyectoraul.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import com.proyectoraul.demo.dto.UsuarioDTO;
import com.proyectoraul.demo.dto.UsuarioPublicoDTO;
import com.proyectoraul.demo.service.UsuarioService;
import org.junit.jupiter.api.Test;

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
}
