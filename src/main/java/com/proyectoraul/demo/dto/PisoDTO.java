package com.proyectoraul.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PisoDTO {
    private Integer IdPiso;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "El codigo del piso no puede superar 100 caracteres")
    private String codigoInvitacion;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer idUsuario;

    public PisoDTO() {
    }

    public PisoDTO(Integer IdPiso, String nombre, String codigoInvitacion, Integer idUsuario) {
        this.IdPiso = IdPiso;
        this.nombre = nombre;
        this.codigoInvitacion = codigoInvitacion;
        this.idUsuario = idUsuario;
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
