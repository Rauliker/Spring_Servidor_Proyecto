package com.proyectoraul.demo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.proyectoraul.demo.dto.GastosDTO;
import com.proyectoraul.demo.model.Gastos;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.GastosRepository;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class GastosServiceImplTest {

    @Test
    void crearAsociaElUsuarioCreadorDelTokenSinCambiarElPagador() {
        GastosRepository gastosRepository = mock(GastosRepository.class);
        PisoRepository pisoRepository = mock(PisoRepository.class);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        GastosServiceImpl service = new GastosServiceImpl(
                gastosRepository, pisoRepository, usuarioRepository);
        Piso piso = new Piso();
        piso.setIdPiso(42);
        Usuario pagador = new Usuario();
        pagador.setIdUsuario(8);
        Usuario creador = new Usuario();
        creador.setIdUsuario(7);
        GastosDTO dto = new GastosDTO();
        dto.setIdPiso(42);
        dto.setIdUsuarioPagador(8);
        dto.setConcepto("Compra");
        dto.setCantidad(new BigDecimal("12.50"));
        dto.setCategorias("Comida");
        dto.setFecha(LocalDate.of(2026, 11, 9));

        when(pisoRepository.findById(42)).thenReturn(Optional.of(piso));
        when(usuarioRepository.findById(8)).thenReturn(Optional.of(pagador));
        when(usuarioRepository.findById(7)).thenReturn(Optional.of(creador));
        when(gastosRepository.save(any(Gastos.class))).thenAnswer(invocation -> {
            Gastos gasto = invocation.getArgument(0);
            gasto.setIdGasto(1);
            return gasto;
        });

        service.create(dto, 7);

        ArgumentCaptor<Gastos> gastoCaptor = ArgumentCaptor.forClass(Gastos.class);
        verify(gastosRepository).save(gastoCaptor.capture());
        assertSame(creador, gastoCaptor.getValue().getUsuarioCreador());
        assertSame(pagador, gastoCaptor.getValue().getUsuarioPagador());
    }
}
