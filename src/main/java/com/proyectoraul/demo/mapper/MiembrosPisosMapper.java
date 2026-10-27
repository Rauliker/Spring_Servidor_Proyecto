
package com.proyectoraul.demo.mapper;

import com.proyectoraul.demo.dto.MiembrosPisosDTO;
import com.proyectoraul.demo.model.MiembrosPiso;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Usuario;

public class MiembrosPisosMapper {

    private MiembrosPisosMapper() {
    }

    public static MiembrosPisosDTO toDTO(MiembrosPiso miembroPiso) {
        if (miembroPiso == null) {
            return null;
        }

        return new MiembrosPisosDTO(
                miembroPiso.getIdMiembro(),
                miembroPiso.getUsuario() != null
                        ? miembroPiso.getUsuario().getIdUsuario().longValue()
                        : null,
                miembroPiso.getPiso() != null
                        ? miembroPiso.getPiso().getIdPiso().longValue()
                        : null,
                miembroPiso.getRol());
    }

    public static MiembrosPiso toEntity(
            MiembrosPisosDTO dto,
            Usuario usuario,
            Piso piso) {

        if (dto == null) {
            return null;
        }

        MiembrosPiso miembroPiso = new MiembrosPiso();

        miembroPiso.setIdMiembro(dto.getIdMiembro());
        miembroPiso.setUsuario(usuario);
        miembroPiso.setPiso(piso);
        miembroPiso.setRol(dto.getRol());

        return miembroPiso;
    }
}
