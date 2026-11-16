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
import com.proyectoraul.demo.dto.UsuarioPublicoDTO;
import com.proyectoraul.demo.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({"/api/usuarios", "/usuarios"})
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
    public List<UsuarioPublicoDTO> listar() {
        return service.listar().stream()
                .map(UsuarioPublicoDTO::desde)
                .toList();
    }

    @GetMapping("/{IdUsuario}")
    public UsuarioPublicoDTO buscarPorIdUsuario(@PathVariable Integer IdUsuario) {
        return UsuarioPublicoDTO.desde(service.buscarPorIdUsuario(IdUsuario));
    }

    @PutMapping("/{IdUsuario}")
    public UsuarioDTO actualizar(@PathVariable Integer IdUsuario, @Valid @RequestBody UsuarioDTO dto) {
        return service.actualizar(IdUsuario, dto);
    }

    @DeleteMapping("/{IdUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer IdUsuario) {
        service.eliminar(IdUsuario);
    }
}
