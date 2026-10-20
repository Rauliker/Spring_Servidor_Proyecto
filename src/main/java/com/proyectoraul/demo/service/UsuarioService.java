package com.proyectoraul.demo.service;

import java.util.List;

import com.proyectoraul.demo.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO crear(UsuarioDTO dto);

    List<UsuarioDTO> listar();

    UsuarioDTO buscarPorIdUsuario(Integer id_usuario);

    UsuarioDTO actualizar(Integer id_usuario, UsuarioDTO dto);

    void eliminar(Integer id_usuario);
}
