package com.proyectoraul.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoraul.demo.dto.GastosDTO;
import com.proyectoraul.demo.service.GastosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gastos")
public class GastosController {

    private final GastosService gastosService;

    public GastosController(GastosService gastosService) {
        this.gastosService = gastosService;
    }

    @GetMapping
    public ResponseEntity<List<GastosDTO>> findAll() {
        return ResponseEntity.ok(gastosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastosDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(gastosService.findById(id));
    }

    @GetMapping("/piso/{idPiso}")
    public ResponseEntity<List<GastosDTO>> findByPiso(@PathVariable Integer idPiso) {
        return ResponseEntity.ok(gastosService.findByPiso(idPiso));
    }

    @PostMapping
    public ResponseEntity<GastosDTO> create(@Valid @RequestBody GastosDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gastosService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastosDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody GastosDTO dto) {
        return ResponseEntity.ok(gastosService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        gastosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
