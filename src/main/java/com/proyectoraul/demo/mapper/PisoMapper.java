package com.proyectoraul.demo.mapper;

import org.springframework.stereotype.Component;

import com.proyectoraul.demo.dto.PisoDTO;
import com.proyectoraul.demo.model.Piso;

@Component
public class PisoMapper {
    public PisoDTO toDTO(Piso piso) {
        return new PisoDTO(
                piso.getIdPiso(), piso.getNombre(), piso.getCodigoInvitacion());
    }

    public Piso toEntity(PisoDTO dto) {
        Piso piso = new Piso();
        piso.setNombre(dto.getNombre());
        piso.setCodigoInvitacion(dto.getCodigoInvitacion());
        return piso;
    }

    public void updateEntity(Piso piso, PisoDTO dto) {
        piso.setNombre(dto.getNombre());
        piso.setCodigoInvitacion(dto.getCodigoInvitacion());
    }
}
