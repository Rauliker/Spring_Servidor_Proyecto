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
        MailNotificationService mailNotificationService = mock(MailNotificationService.class);
        MiembrosPisosServiceImpl service = new MiembrosPisosServiceImpl(
                miembrosRepository, usuarioRepository, pisoRepository, mailNotificationService);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(7);
        Usuario creador = new Usuario();
        creador.setIdUsuario(8);
        Piso piso = new Piso();
        piso.setIdPiso(42);
        piso.setUsuarioCreador(creador);

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

        MiembrosPisosDTO creado = service.create(7, "codigo-piso", Role.ADMIN);

        assertEquals(7L, creado.getIdUsuario());
        assertEquals(42L, creado.getIdPiso());
        assertEquals(Role.MIEMBRO, creado.getRol());
        verify(pisoRepository).buscarPorCodigoPisoSQL("codigo-piso");
        verify(mailNotificationService).enviarAvisoAltaEnPiso(usuario, piso);
    }

    @Test
    void crearAsignaAdminCuandoElUsuarioEsCreadorDelPiso() {
        MiembrosPisosRepository miembrosRepository = mock(MiembrosPisosRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        PisoRepository pisoRepository = mock(PisoRepository.class);
        MailNotificationService mailNotificationService = mock(MailNotificationService.class);
        MiembrosPisosServiceImpl service = new MiembrosPisosServiceImpl(
                miembrosRepository, usuarioRepository, pisoRepository, mailNotificationService);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(7);
        Piso piso = new Piso();
        piso.setIdPiso(42);
        piso.setUsuarioCreador(usuario);

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

        assertEquals(Role.ADMIN, creado.getRol());
    }

    @Test
    void eliminarMembresiaEnviaAvisoAlUsuarioAntesDeEliminarla() {
        MiembrosPisosRepository miembrosRepository = mock(MiembrosPisosRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        PisoRepository pisoRepository = mock(PisoRepository.class);
        MailNotificationService mailNotificationService = mock(MailNotificationService.class);
        MiembrosPisosServiceImpl service = new MiembrosPisosServiceImpl(
                miembrosRepository, usuarioRepository, pisoRepository, mailNotificationService);
        Usuario usuario = new Usuario(7, "Raul", "raul", "raul@example.com", "hash");
        Piso piso = new Piso();
        piso.setNombre("Piso Centro");
        MiembrosPiso miembro = new MiembrosPiso(100L, usuario, piso, Role.MIEMBRO);
        when(miembrosRepository.findById(100L)).thenReturn(Optional.of(miembro));

        service.delete(100L);

        verify(mailNotificationService).enviarAvisoSalidaDePiso(usuario, piso);
        verify(miembrosRepository).delete(miembro);
    }
}
