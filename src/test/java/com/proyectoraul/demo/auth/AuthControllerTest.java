package com.proyectoraul.demo.auth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AuthControllerTest {

    private MockMvc mockMvc;
    private UsuarioRepository usuarioRepository;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        tokenService = mock(TokenService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new AuthController(usuarioRepository, tokenService, passwordEncoder))
                .build();
    }

    @Test
    void loginConCredencialesValidasDevuelveToken() throws Exception {
        Usuario usuario = new Usuario(1, "Raul", "raul", "raul@example.com", "hash");
        when(usuarioRepository.buscarPorEmailSQL("raul@example.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("clave", "hash")).thenReturn(true);
        when(tokenService.generarToken(1, "raul", "raul@example.com", "Raul")).thenReturn("token-prueba");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"raul@example.com","password":"clave"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("token-prueba"));
    }

    @Test
    void loginConUsuarioInexistenteDevuelveNoAutorizado() throws Exception {
        when(usuarioRepository.buscarPorEmailSQL("nadie@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"nadie@example.com","password":"clave"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Email o contraseña incorrectos"));
    }

    @Test
    void loginConContrasenaIncorrectaDevuelveNoAutorizado() throws Exception {
        Usuario usuario = new Usuario(1, "Raul", "raul", "raul@example.com", "hash");
        when(usuarioRepository.buscarPorEmailSQL("raul@example.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("incorrecta", "hash")).thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"raul@example.com","password":"incorrecta"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Email o contraseña incorrectos"));
    }
}
