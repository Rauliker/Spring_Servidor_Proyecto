package com.proyectoraul.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoraul.demo.dto.UsuarioDTO;
import com.proyectoraul.demo.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO crear(@Valid @RequestBody UsuarioDTO dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id_usuario}")
    public UsuarioDTO buscarPorIdUsuario(@PathVariable Integer id_usuario) {
        return service.buscarPorIdUsuario(id_usuario);
    }

    @PutMapping("/{id_usuario}")
    public UsuarioDTO actualizar(@PathVariable Integer id_usuario, @Valid @RequestBody UsuarioDTO dto) {
        return service.actualizar(id_usuario, dto);
    }

    @DeleteMapping("/{id_usuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id_usuario) {
        service.eliminar(id_usuario);
    }
}
