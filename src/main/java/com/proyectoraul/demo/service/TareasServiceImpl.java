package com.proyectoraul.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.proyectoraul.demo.dto.TareasDTO;
import com.proyectoraul.demo.mapper.TareasMapper;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Tareas;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.repository.TareasRepository;
import com.proyectoraul.demo.repository.UsuarioRepository;

@Service
@Transactional
public class TareasServiceImpl implements TareasService {

    private final TareasRepository tareasRepository;
    private final PisoRepository pisoRepository;
    private final UsuarioRepository usuarioRepository;

    public TareasServiceImpl(
            TareasRepository tareasRepository,
            PisoRepository pisoRepository,
            UsuarioRepository usuarioRepository) {
        this.tareasRepository = tareasRepository;
        this.pisoRepository = pisoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareasDTO> findAll() {
        return tareasRepository.findAll()
                .stream()
                .map(TareasMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareasDTO> findByPiso(Integer idPiso) {
        return tareasRepository.findByPiso_IdPisoOrderByFechaLimiteAsc(idPiso)
                .stream()
                .map(TareasMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TareasDTO findById(Integer id) {
        return TareasMapper.toDTO(findTarea(id));
    }

    @Override
    public TareasDTO create(TareasDTO dto, Integer idUsuarioCreador) {
        Piso piso = findPiso(dto.getIdPiso());
        Usuario usuarioAsignado = findUsuario(dto.getIdUsuarioAsignado());
        Usuario usuarioCreador = usuarioRepository.findById(idUsuarioCreador)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario creador no encontrado"));

        Tareas tarea = TareasMapper.toEntity(
                dto, piso, usuarioAsignado, usuarioCreador);
        tarea.setIdTarea(null);
        return TareasMapper.toDTO(tareasRepository.save(tarea));
    }

    @Override
    public TareasDTO update(Integer id, TareasDTO dto) {
        Tareas tarea = findTarea(id);
        Piso piso = findPiso(dto.getIdPiso());
        Usuario usuarioAsignado = findUsuario(dto.getIdUsuarioAsignado());

        tarea.setPiso(piso);
        tarea.setUsuarioAsignado(usuarioAsignado);
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

        return TareasMapper.toDTO(tareasRepository.save(tarea));
    }

    @Override
    public void delete(Integer id) {
        tareasRepository.delete(findTarea(id));
    }

    private Tareas findTarea(Integer id) {
        return tareasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tarea no encontrada"));
    }

    private Piso findPiso(Integer idPiso) {
        return pisoRepository.findById(idPiso)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Piso no encontrado"));
    }

    private Usuario findUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario asignado no encontrado"));
    }
}
