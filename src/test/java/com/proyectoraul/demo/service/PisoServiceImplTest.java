package com.proyectoraul.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.proyectoraul.demo.dto.PisoDTO;
import com.proyectoraul.demo.mapper.PisoMapper;
import com.proyectoraul.demo.model.MiembrosPiso;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Role;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.MiembrosPisosRepository;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class PisoServiceImplTest {

    @Test
    void crearAnadeAlCreadorComoAdminDelPiso() {
        PisoRepository pisoRepository = mock(PisoRepository.class);
        PisoMapper mapper = mock(PisoMapper.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        MiembrosPisosRepository miembrosPisosRepository =
                mock(MiembrosPisosRepository.class);
        PisoServiceImpl service = new PisoServiceImpl(
                pisoRepository, mapper, usuarioRepository, miembrosPisosRepository);
        Usuario creador = new Usuario();
        creador.setIdUsuario(7);
        Piso piso = new Piso();
        PisoDTO dto = new PisoDTO();

        when(pisoRepository.buscarPorCodigoPisoSQL(anyString()))
                .thenReturn(Optional.empty());
        when(mapper.toEntity(dto)).thenReturn(piso);
        when(usuarioRepository.findById(7)).thenReturn(Optional.of(creador));
        when(pisoRepository.save(piso)).thenAnswer(invocation -> {
            piso.setIdPiso(42);
            return piso;
        });
        when(mapper.toDTO(piso)).thenReturn(new PisoDTO(42, "Piso", "codigo", 7));

        service.crear(dto, 7);

        ArgumentCaptor<MiembrosPiso> miembroCaptor =
                ArgumentCaptor.forClass(MiembrosPiso.class);
        verify(miembrosPisosRepository).save(miembroCaptor.capture());
        MiembrosPiso miembro = miembroCaptor.getValue();
        assertSame(creador, miembro.getUsuario());
        assertSame(piso, miembro.getPiso());
        assertEquals(Role.ADMIN, miembro.getRol());
    }
}
