package com.proyectoraul.demo.service;

import java.util.List;

import com.proyectoraul.demo.dto.TareasDTO;

public interface TareasService {

    List<TareasDTO> findAll();

    List<TareasDTO> findByPiso(Integer idPiso);

    TareasDTO findById(Integer id);

    TareasDTO create(TareasDTO dto, Integer idUsuarioCreador);

    TareasDTO update(Integer id, TareasDTO dto);

    void delete(Integer id);
}
