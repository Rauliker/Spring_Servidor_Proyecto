package com.proyectoraul.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoraul.demo.model.Gastos;

public interface GastosRepository extends JpaRepository<Gastos, Integer> {

    List<Gastos> findByPiso_IdPisoOrderByFechaDesc(Integer idPiso);
}
