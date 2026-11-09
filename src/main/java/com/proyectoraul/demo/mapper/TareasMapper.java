package com.proyectoraul.demo.mapper;

import java.time.LocalDate;

import com.proyectoraul.demo.dto.TareasDTO;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Tareas;
import com.proyectoraul.demo.model.Usuario;

public class TareasMapper {

    private TareasMapper() {
    }

    public static TareasDTO toDTO(Tareas tarea) {
        if (tarea == null) {
            return null;
        }

        return new TareasDTO(
                tarea.getIdTarea(),
                tarea.getPiso() != null ? tarea.getPiso().getIdPiso() : null,
                tarea.getUsuarioAsignado() != null ? tarea.getUsuarioAsignado().getIdUsuario() : null,
                tarea.getUsuarioCreador() != null ? tarea.getUsuarioCreador().getIdUsuario() : null,
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getFechaLimite(),
                tarea.isCompletada(),
                tarea.getFechaCompletada());
    }

    public static Tareas toEntity(
            TareasDTO dto, Piso piso, Usuario usuarioAsignado, Usuario usuarioCreador) {
        if (dto == null) {
            return null;
        }

        Tareas tarea = new Tareas();
        tarea.setIdTarea(dto.getIdTarea());
        tarea.setPiso(piso);
        tarea.setUsuarioAsignado(usuarioAsignado);
        tarea.setUsuarioCreador(usuarioCreador);
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setFechaLimite(dto.getFechaLimite());

        boolean completada = Boolean.TRUE.equals(dto.getCompletada());
        tarea.setCompletada(completada);
        if (completada) {
            tarea.setFechaCompletada(dto.getFechaCompletada() != null ? dto.getFechaCompletada() : LocalDate.now());
        } else {
            tarea.setFechaCompletada(null);
        }

        return tarea;
    }
}
