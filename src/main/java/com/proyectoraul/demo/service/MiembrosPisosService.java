package com.proyectoraul.demo.service;

import java.util.List;

import com.proyectoraul.demo.dto.MiembrosPisosDTO;

public interface MiembrosPisosService {

    List<MiembrosPisosDTO> findAll();

    MiembrosPisosDTO findById(Long id);

    MiembrosPisosDTO create(MiembrosPisosDTO dto);

    MiembrosPisosDTO update(Long id, MiembrosPisosDTO dto);

    void delete(Long id);
}
