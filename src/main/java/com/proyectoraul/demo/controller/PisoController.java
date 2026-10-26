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

import com.proyectoraul.demo.dto.PisoDTO;
import com.proyectoraul.demo.service.PisoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pisos")
public class PisoController {
    private final PisoService service;

    public PisoController(PisoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PisoDTO crear(@Valid @RequestBody PisoDTO dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<PisoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{IdPiso}")
    public PisoDTO buscarPorIdPiso(@PathVariable Integer IdPiso) {
        return service.buscarPorIdPiso(IdPiso);
    }

    @PutMapping("/{IdPiso}")
    public PisoDTO actualizar(@PathVariable Integer IdPiso, @Valid @RequestBody PisoDTO dto) {
        return service.actualizar(IdPiso, dto);
    }

    @DeleteMapping("/{IdPiso}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer IdPiso) {
        service.eliminar(IdPiso);
    }
}
