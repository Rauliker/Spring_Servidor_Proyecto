package com.proyectoraul.demo.mapper;

import org.springframework.stereotype.Component;

import com.proyectoraul.demo.dto.UsuarioDTO;
import com.proyectoraul.demo.model.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getIdUsuario(), usuario.getNombre(), usuario.getUserName(), usuario.getEmail(),
                usuario.getPassword());
    }

    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setUserName(dto.getUserName());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        return usuario;
    }

    public void updateEntity(Usuario usuario, UsuarioDTO dto) {
        usuario.setNombre(dto.getNombre());
        usuario.setUserName(dto.getUserName());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
    }
}
