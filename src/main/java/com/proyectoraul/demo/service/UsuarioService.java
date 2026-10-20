package com.proyectoraul.demo.service;

import java.util.List;

import com.proyectoraul.demo.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO crear(UsuarioDTO dto);

    List<UsuarioDTO> listar();

    UsuarioDTO buscarPorIdUsuario(Integer IdUsuario);

    UsuarioDTO actualizar(Integer IdUsuario, UsuarioDTO dto);

    void eliminar(Integer IdUsuario);
}
