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

import com.proyectoraul.demo.dto.TareasDTO;
import com.proyectoraul.demo.service.TareasService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tareas")
public class TareasController {

    private final TareasService tareasService;

    public TareasController(TareasService tareasService) {
        this.tareasService = tareasService;
    }

    @GetMapping
    public ResponseEntity<List<TareasDTO>> findAll() {
        return ResponseEntity.ok(tareasService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareasDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(tareasService.findById(id));
    }

    @GetMapping("/piso/{idPiso}")
    public ResponseEntity<List<TareasDTO>> findByPiso(@PathVariable Integer idPiso) {
        return ResponseEntity.ok(tareasService.findByPiso(idPiso));
    }

    @PostMapping
    public ResponseEntity<TareasDTO> create(@Valid @RequestBody TareasDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tareasService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareasDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TareasDTO dto) {
        return ResponseEntity.ok(tareasService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tareasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
