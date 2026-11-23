package com.proyectoraul.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.proyectoraul.demo.dto.MiembrosPisosDTO;
import com.proyectoraul.demo.model.MiembrosPiso;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Role;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.GastosRepository;
import com.proyectoraul.demo.repository.MiembrosPisosRepository;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.repository.TareasRepository;
import com.proyectoraul.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;

class MiembrosPisosServiceImplTest {

    @Test
    void crearAsignaMiembroCuandoElUsuarioNoEsCreadorYNoSeEnviaRol() {
        MiembrosPisosRepository miembrosRepository = mock(MiembrosPisosRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        PisoRepository pisoRepository = mock(PisoRepository.class);
        GastosRepository gastosRepository = mock(GastosRepository.class);
        TareasRepository tareasRepository = mock(TareasRepository.class);
        MiembrosPisosServiceImpl service = new MiembrosPisosServiceImpl(
                miembrosRepository, usuarioRepository, pisoRepository,
                gastosRepository, tareasRepository);
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

        MiembrosPisosDTO creado = service.create(7, "codigo-piso", null);

        assertEquals(7L, creado.getIdUsuario());
        assertEquals(42L, creado.getIdPiso());
        assertEquals(Role.MIEMBRO, creado.getRol());
        verify(pisoRepository).buscarPorCodigoPisoSQL("codigo-piso");
    }

    @Test
    void crearAsignaAdminCuandoElUsuarioEsCreadorDelPiso() {
        MiembrosPisosRepository miembrosRepository = mock(MiembrosPisosRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        PisoRepository pisoRepository = mock(PisoRepository.class);
        GastosRepository gastosRepository = mock(GastosRepository.class);
        TareasRepository tareasRepository = mock(TareasRepository.class);
        MiembrosPisosServiceImpl service = new MiembrosPisosServiceImpl(
                miembrosRepository, usuarioRepository, pisoRepository,
                gastosRepository, tareasRepository);
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
    void eliminarMiembroBorraSusGastosYTareasSoloEnSuPisoAntesDeEliminarLaMembresia() {
        MiembrosPisosRepository miembrosRepository = mock(MiembrosPisosRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        PisoRepository pisoRepository = mock(PisoRepository.class);
        GastosRepository gastosRepository = mock(GastosRepository.class);
        TareasRepository tareasRepository = mock(TareasRepository.class);
        MiembrosPisosServiceImpl service = new MiembrosPisosServiceImpl(
                miembrosRepository, usuarioRepository, pisoRepository,
                gastosRepository, tareasRepository);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(7);
        Piso piso = new Piso();
        piso.setIdPiso(42);
        MiembrosPiso miembro = new MiembrosPiso(100L, usuario, piso, Role.MIEMBRO);
        when(miembrosRepository.findById(100L)).thenReturn(Optional.of(miembro));

        service.delete(100L);

        var inOrder = inOrder(gastosRepository, tareasRepository, miembrosRepository);
        inOrder.verify(gastosRepository).deleteRelacionadosConUsuarioEnPiso(42, 7);
        inOrder.verify(tareasRepository).deleteRelacionadasConUsuarioEnPiso(42, 7);
        inOrder.verify(miembrosRepository).delete(miembro);
    }
}
