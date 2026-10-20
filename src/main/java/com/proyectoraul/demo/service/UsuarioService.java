package com.proyectoraul.demo.service;

import java.util.List;

import com.proyectoraul.demo.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO crear(UsuarioDTO dto);

    List<UsuarioDTO> listar();

    UsuarioDTO buscarPorId(Long id);

    UsuarioDTO actualizar(Long id, UsuarioDTO dto);

    void eliminar(Long id);
}
