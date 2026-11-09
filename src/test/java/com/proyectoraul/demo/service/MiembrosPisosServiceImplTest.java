package com.proyectoraul.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.proyectoraul.demo.dto.MiembrosPisosDTO;
import com.proyectoraul.demo.model.MiembrosPiso;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Role;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.MiembrosPisosRepository;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;

class MiembrosPisosServiceImplTest {

    @Test
    void crearBuscaElPisoPorCodigoYAsociaElUsuarioDelToken() {
        MiembrosPisosRepository miembrosRepository = mock(MiembrosPisosRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        PisoRepository pisoRepository = mock(PisoRepository.class);
        MiembrosPisosServiceImpl service = new MiembrosPisosServiceImpl(
                miembrosRepository, usuarioRepository, pisoRepository);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(7);
        Piso piso = new Piso();
        piso.setIdPiso(42);

        when(usuarioRepository.findById(7)).thenReturn(Optional.of(usuario));
        when(pisoRepository.buscarPorCodigoPisoSQL("codigo-piso"))
                .thenReturn(Optional.of(piso));
        when(miembrosRepository.existsByUsuario_IdUsuarioAndPiso_IdPiso(7L, 42L))
                .thenReturn(false);
        when(miembrosRepository.save(any(MiembrosPiso.class))).thenAnswer(invocation -> {
            MiembrosPiso miembro = invocation.getArgument(0);
            miembro.setIdMiembro(100L);
            return miembro;
        });

        MiembrosPisosDTO creado = service.create(7, "codigo-piso", Role.MIEMBRO);

        assertEquals(7L, creado.getIdUsuario());
        assertEquals(42L, creado.getIdPiso());
        verify(pisoRepository).buscarPorCodigoPisoSQL("codigo-piso");
    }
}
