package com.proyectoraul.demo.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "pisos")
public class Piso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pisoo")
    private Integer idPiso;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "\"codigo_invitacion\"", nullable = true, length = 100)
    private String codigoInvitacion;

    public Piso() {
    }

    public Piso(Integer idPiso, String nombre, String codigoInvitacion) {
        this.idPiso = idPiso;
        this.nombre = nombre;
        this.codigoInvitacion = codigoInvitacion;
    }

    public Integer getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(Integer idPiso) {
        this.idPiso = idPiso;
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
