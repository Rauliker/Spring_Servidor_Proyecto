package com.proyectoraul.demo.auth;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final UsuarioRepository usuarioRepository;
        private final TokenService tokenService;
        private final PasswordEncoder passwordEncoder;

        public AuthController(
                        UsuarioRepository usuarioRepository,
                        TokenService tokenService,
                        PasswordEncoder passwordEncoder) {
                this.usuarioRepository = usuarioRepository;
                this.tokenService = tokenService;
                this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(
                        @Valid @RequestBody LoginRequest request) {

                Usuario usuario = usuarioRepository
                                .buscarPorEmailSQL(request.email())
                                .orElse(null);

                System.out.println("Usuario encontrado: " + usuario.getEmail());

                boolean coincide = ((request.password()) == (usuario.getPassword()));

                System.out.println("Contraseña correcta: " + coincide + " " + request.password() + " "
                                + usuario.getPassword());
                if (usuario == null
                                || !passwordEncoder.matches(
                                                request.password(),
                                                usuario.getPassword())) {

                        return ResponseEntity
                                        .status(HttpStatus.UNAUTHORIZED)
                                        .body(Map.of(
                                                        "error",
                                                        "Email o contraseña incorrectos"));
                }

                String token = tokenService.generarToken(
                                usuario.getIdUsuario(),
                                usuario.getUserName(),
                                usuario.getEmail(),
                                usuario.getNombre());

                return ResponseEntity.ok(
                                Map.of("accessToken", token));
        }
}