package com.proyectoraul.demo.dto;

public record UsuarioPublicoDTO(Integer idUsuario, String nombre, String userName) {

    public static UsuarioPublicoDTO desde(UsuarioDTO usuario) {
        return new UsuarioPublicoDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getUserName());
    }
}
