package com.proyectoraul.demo.dto;

import com.proyectoraul.demo.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearMiembroPisoDTO {

    @NotBlank(message = "El código de invitación es obligatorio")
    @Size(max = 100, message = "El código de invitación no puede superar 100 caracteres")
    private String codigoInvitacion;

    @NotNull(message = "El rol es obligatorio")
    private Role rol;

    public String getCodigoInvitacion() {
        return codigoInvitacion;
    }

    public void setCodigoInvitacion(String codigoInvitacion) {
        this.codigoInvitacion = codigoInvitacion;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }
}
