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

import com.proyectoraul.demo.dto.MiembrosPisosDTO;
import com.proyectoraul.demo.service.MiembrosPisosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/miembros-pisos")
public class MiembrosPisosController {

    private final MiembrosPisosService miembrosPisosService;

    public MiembrosPisosController(
            MiembrosPisosService miembrosPisosService) {
        this.miembrosPisosService = miembrosPisosService;
    }

    // Obtener todas las membresías
    @GetMapping
    public ResponseEntity<List<MiembrosPisosDTO>> findAll() {
        return ResponseEntity.ok(
                miembrosPisosService.findAll());
    }

    // Obtener una membresía por ID
    @GetMapping("/{id}")
    public ResponseEntity<MiembrosPisosDTO> findById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                miembrosPisosService.findById(id));
    }

    // Añadir un usuario a un piso
    @PostMapping
    public ResponseEntity<MiembrosPisosDTO> create(
            @Valid @RequestBody MiembrosPisosDTO dto) {
        MiembrosPisosDTO creado = miembrosPisosService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creado);
    }

    // Actualizar una membresía
    @PutMapping("/{id}")
    public ResponseEntity<MiembrosPisosDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody MiembrosPisosDTO dto) {
        return ResponseEntity.ok(
                miembrosPisosService.update(id, dto));
    }

    // Eliminar una membresía
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {
        miembrosPisosService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
