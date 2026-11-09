package com.proyectoraul.demo.service;

import java.util.List;

import com.proyectoraul.demo.dto.MiembrosPisosDTO;
import com.proyectoraul.demo.model.Role;

public interface MiembrosPisosService {

    List<MiembrosPisosDTO> findAll();

    MiembrosPisosDTO findById(Long id);

    MiembrosPisosDTO create(Integer idUsuario, String codigoInvitacion, Role rol);

    MiembrosPisosDTO update(Long id, MiembrosPisosDTO dto);

    void delete(Long id);
}
