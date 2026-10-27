
package com.proyectoraul.demo.dto;

import com.proyectoraul.demo.model.Role;

import jakarta.validation.constraints.NotNull;

public class MiembrosPisosDTO {

    private Long idMiembro;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID del piso es obligatorio")
    private Long idPiso;

    @NotNull(message = "El rol es obligatorio")
    private Role rol;

    public MiembrosPisosDTO() {
    }

    public MiembrosPisosDTO(
            Long idMiembro,
            Long idUsuario,
            Long idPiso,
            Role rol) {
        this.idMiembro = idMiembro;
        this.idUsuario = idUsuario;
        this.idPiso = idPiso;
        this.rol = rol;
    }

    public Long getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Long idMiembro) {
        this.idMiembro = idMiembro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(Long idPiso) {
        this.idPiso = idPiso;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }
}
