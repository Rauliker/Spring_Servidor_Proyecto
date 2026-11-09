package com.proyectoraul.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "pisos", uniqueConstraints = @UniqueConstraint(columnNames = "\"codigo_invitacion\""))
public class Piso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piso")
    private Integer idPiso;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "\"codigo_invitacion\"", nullable = false, length = 100)
    private String codigoInvitacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuarioCreador;

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

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
}
