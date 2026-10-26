package com.proyectoraul.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PisoDTO {
    private Integer IdPiso;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "El codigo del piso no puede superar 100 caracteres")
    private String codigoInvitacion;

    public PisoDTO() {
    }

    public PisoDTO(Integer IdPiso, String nombre, String codigoInvitacion) {
        this.IdPiso = IdPiso;
        this.nombre = nombre;
        this.codigoInvitacion = codigoInvitacion;
    }

    public Integer getIdPiso() {
        return IdPiso;
    }

    public void setIdPiso(Integer idPiso) {
        IdPiso = idPiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoInvitacion() {
        return codigoInvitacion;
    }

    public void setCodigoInvitacion(String codigoInvitacion) {
        this.codigoInvitacion = codigoInvitacion;
    }
}
