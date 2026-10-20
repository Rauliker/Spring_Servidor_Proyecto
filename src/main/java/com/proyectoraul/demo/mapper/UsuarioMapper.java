package com.proyectoraul.demo.mapper;

import org.springframework.stereotype.Component;

import com.proyectoraul.demo.dto.UsuarioDTO;
import com.proyectoraul.demo.model.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getIdUsuario(), usuario.getNombre(), usuario.getEmail(), usuario.getUserName(),
                usuario.getPassword());
    }

    public Usuario toEntity(UsuarioDTO dto) {
        return new Usuario(dto.getIdUsuario(), dto.getNombre(), dto.getEmail(), dto.getUserName(), dto.getPassword());
    }

    public void updateEntity(Usuario usuario, UsuarioDTO dto) {
        usuario.setNombre(dto.getNombre());
        usuario.setUserName(dto.getUserName());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
    }
}
