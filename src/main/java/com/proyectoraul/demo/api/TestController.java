package com.proyectoraul.demo.api;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/privado")
    public Map<String, String> privado(
            @AuthenticationPrincipal Jwt jwt) {

        return Map.of(
                "mensaje", "Acceso autorizado",
                "usuario", jwt.getSubject());
    }
}