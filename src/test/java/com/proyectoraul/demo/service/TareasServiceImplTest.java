package com.proyectoraul.demo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import com.proyectoraul.demo.dto.TareasDTO;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Tareas;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.repository.TareasRepository;
import com.proyectoraul.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class TareasServiceImplTest {

    @Test
    void crearAsociaElUsuarioCreadorObtenidoDelToken() {
        TareasRepository tareasRepository = mock(TareasRepository.class);
        PisoRepository pisoRepository = mock(PisoRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        TareasServiceImpl service = new TareasServiceImpl(
                tareasRepository, pisoRepository, usuarioRepository);
        Piso piso = new Piso();
        piso.setIdPiso(42);
        Usuario asignado = new Usuario();
        asignado.setIdUsuario(8);
        Usuario creador = new Usuario();
        creador.setIdUsuario(7);
        TareasDTO dto = new TareasDTO();
        dto.setIdPiso(42);
        dto.setIdUsuarioAsignado(8);
        dto.setTitulo("Limpiar cocina");
        dto.setDescripcion("Limpiar la cocina");
        dto.setFechaLimite(LocalDate.of(2026, 11, 10));
        dto.setCompletada(false);

        when(pisoRepository.findById(42)).thenReturn(Optional.of(piso));
        when(usuarioRepository.findById(8)).thenReturn(Optional.of(asignado));
        when(usuarioRepository.findById(7)).thenReturn(Optional.of(creador));
        when(tareasRepository.save(any(Tareas.class))).thenAnswer(invocation -> {
            Tareas tarea = invocation.getArgument(0);
            tarea.setIdTarea(1);
            return tarea;
        });

        service.create(dto, 7);

        ArgumentCaptor<Tareas> tareaCaptor = ArgumentCaptor.forClass(Tareas.class);
        verify(tareasRepository).save(tareaCaptor.capture());
        assertSame(creador, tareaCaptor.getValue().getUsuarioCreador());
        assertSame(asignado, tareaCaptor.getValue().getUsuarioAsignado());
    }
}
