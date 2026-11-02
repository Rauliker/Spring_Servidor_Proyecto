package com.proyectoraul.demo.service;

import java.util.List;

import com.proyectoraul.demo.dto.GastosDTO;

public interface GastosService {

    List<GastosDTO> findAll();

    List<GastosDTO> findByPiso(Integer idPiso);

    GastosDTO findById(Integer id);

    GastosDTO create(GastosDTO dto);

    GastosDTO update(Integer id, GastosDTO dto);

    void delete(Integer id);
}
