package com.proyectoraul.demo.mapper;

import com.proyectoraul.demo.dto.GastosDTO;
import com.proyectoraul.demo.model.Gastos;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Usuario;

public class GastosMapper {

    private GastosMapper() {
    }

    public static GastosDTO toDTO(Gastos gasto) {
        if (gasto == null) {
            return null;
        }

        return new GastosDTO(
                gasto.getIdGasto(),
                gasto.getPiso() != null ? gasto.getPiso().getIdPiso() : null,
                gasto.getUsuarioPagador() != null
                        ? gasto.getUsuarioPagador().getIdUsuario()
                        : null,
                gasto.getConcepto(),
                gasto.getCantidad(),
                gasto.getCategorias(),
                gasto.getFecha());
    }

    public static Gastos toEntity(GastosDTO dto, Piso piso, Usuario usuarioPagador) {
        if (dto == null) {
            return null;
        }

        Gastos gasto = new Gastos();
        gasto.setIdGasto(dto.getIdGasto());
        gasto.setPiso(piso);
        gasto.setUsuarioPagador(usuarioPagador);
        gasto.setConcepto(dto.getConcepto());
        gasto.setCantidad(dto.getCantidad());
        gasto.setCategorias(dto.getCategorias());
        gasto.setFecha(dto.getFecha());
        return gasto;
    }
}
