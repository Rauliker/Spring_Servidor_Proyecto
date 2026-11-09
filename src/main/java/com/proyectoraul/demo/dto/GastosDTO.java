package com.proyectoraul.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GastosDTO {

    private Integer idGasto;

    @NotNull(message = "El ID del piso es obligatorio")
    private Integer idPiso;

    @NotNull(message = "El ID del usuario pagador es obligatorio")
    private Integer idUsuarioPagador;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer idUsuarioCreador;

    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 150, message = "El concepto no puede superar los 150 caracteres")
    private String concepto;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor que cero")
    @Digits(integer = 8, fraction = 2, message = "La cantidad admite hasta 8 enteros y 2 decimales")
    private BigDecimal cantidad;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede superar los 50 caracteres")
    private String categorias;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    public GastosDTO() {
    }

    public GastosDTO(
            Integer idGasto,
            Integer idPiso,
            Integer idUsuarioPagador,
            Integer idUsuarioCreador,
            String concepto,
            BigDecimal cantidad,
            String categorias,
            LocalDate fecha) {
        this.idGasto = idGasto;
        this.idPiso = idPiso;
        this.idUsuarioPagador = idUsuarioPagador;
        this.idUsuarioCreador = idUsuarioCreador;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.categorias = categorias;
        this.fecha = fecha;
    }

    public Integer getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(Integer idGasto) {
        this.idGasto = idGasto;
    }

    public Integer getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(Integer idPiso) {
        this.idPiso = idPiso;
    }

    public Integer getIdUsuarioPagador() {
        return idUsuarioPagador;
    }

    public void setIdUsuarioPagador(Integer idUsuarioPagador) {
        this.idUsuarioPagador = idUsuarioPagador;
    }

    public Integer getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(Integer idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
