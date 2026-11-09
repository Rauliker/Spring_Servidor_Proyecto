package com.proyectoraul.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.proyectoraul.demo.dto.GastosDTO;
import com.proyectoraul.demo.mapper.GastosMapper;
import com.proyectoraul.demo.model.Gastos;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.GastosRepository;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.repository.UsuarioRepository;

@Service
@Transactional
public class GastosServiceImpl implements GastosService {

    private final GastosRepository gastosRepository;
    private final PisoRepository pisoRepository;
    private final UsuarioRepository usuarioRepository;

    public GastosServiceImpl(
            GastosRepository gastosRepository,
            PisoRepository pisoRepository,
            UsuarioRepository usuarioRepository) {
        this.gastosRepository = gastosRepository;
        this.pisoRepository = pisoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GastosDTO> findAll() {
        return gastosRepository.findAll()
                .stream()
                .map(GastosMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GastosDTO> findByPiso(Integer idPiso) {
        return gastosRepository.findByPiso_IdPisoOrderByFechaDesc(idPiso)
                .stream()
                .map(GastosMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GastosDTO findById(Integer id) {
        return GastosMapper.toDTO(findGasto(id));
    }

    @Override
    public GastosDTO create(GastosDTO dto, Integer idUsuarioCreador) {
        Piso piso = findPiso(dto.getIdPiso());
        Usuario usuarioPagador = findUsuario(dto.getIdUsuarioPagador());
        Usuario usuarioCreador = usuarioRepository.findById(idUsuarioCreador)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario creador no encontrado"));

        Gastos gasto = GastosMapper.toEntity(
                dto, piso, usuarioPagador, usuarioCreador);
        gasto.setIdGasto(null);
        return GastosMapper.toDTO(gastosRepository.save(gasto));
    }

    @Override
    public GastosDTO update(Integer id, GastosDTO dto) {
        Gastos gasto = findGasto(id);
        Piso piso = findPiso(dto.getIdPiso());
        Usuario usuarioPagador = findUsuario(dto.getIdUsuarioPagador());

        gasto.setPiso(piso);
        gasto.setUsuarioPagador(usuarioPagador);
        gasto.setConcepto(dto.getConcepto());
        gasto.setCantidad(dto.getCantidad());
        gasto.setCategorias(dto.getCategorias());
        gasto.setFecha(dto.getFecha());

        return GastosMapper.toDTO(gastosRepository.save(gasto));
    }

    @Override
    public void delete(Integer id) {
        gastosRepository.delete(findGasto(id));
    }

    private Gastos findGasto(Integer id) {
        return gastosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Gasto no encontrado"));
    }

    private Piso findPiso(Integer idPiso) {
        return pisoRepository.findById(idPiso)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Piso no encontrado"));
    }

    private Usuario findUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario pagador no encontrado"));
    }
}
