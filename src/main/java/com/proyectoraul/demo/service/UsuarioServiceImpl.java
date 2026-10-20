package com.proyectoraul.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.proyectoraul.demo.dto.UsuarioDTO;
import com.proyectoraul.demo.mapper.UsuarioMapper;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioServiceImpl(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UsuarioDTO crear(UsuarioDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }
        Usuario usuario = mapper.toEntity(dto);
        usuario.setId(null);
        return mapper.toDTO(repository.save(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listar() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        return mapper.toDTO(obtenerUsuario(id));
    }

    @Override
    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        Usuario existente = obtenerUsuario(id);
        repository.findByEmail(dto.getEmail()).ifPresent(otro -> {
            if (!otro.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ese email ya pertenece a otro usuario");
            }
        });
        mapper.updateEntity(existente, dto);
        return mapper.toDTO(repository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = obtenerUsuario(id);
        repository.delete(usuario);
    }

    private Usuario obtenerUsuario(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado: " + id));
    }
}
