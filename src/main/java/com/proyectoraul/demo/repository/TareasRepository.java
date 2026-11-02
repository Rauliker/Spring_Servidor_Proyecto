package com.proyectoraul.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoraul.demo.model.Tareas;

public interface TareasRepository extends JpaRepository<Tareas, Integer> {

    List<Tareas> findByPiso_IdPisoOrderByFechaLimiteAsc(Integer idPiso);

    List<Tareas> findByUsuarioAsignado_IdUsuarioOrderByFechaLimiteAsc(Integer idUsuario);
}
