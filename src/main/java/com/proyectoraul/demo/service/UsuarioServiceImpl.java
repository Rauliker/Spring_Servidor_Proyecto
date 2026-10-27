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
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioServiceImpl(
            UsuarioRepository repository,
            UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UsuarioDTO crear(UsuarioDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un usuario con ese email");
        }

        Usuario usuario = mapper.toEntity(dto);

        // el ID lo genera PostgreSQL
        usuario.setIdUsuario(null);

        // guarda el usuario en la base de datos
        Usuario guardado = repository.save(usuario);

        // devuelve el usuario guardado, con su ID
        return mapper.toDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listar() {
        return repository.listarUsuarios()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorIdUsuario(Integer IdUsuario) {
        Usuario usuario = repository.buscarPorIdUsuarioSQL(IdUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return mapper.toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizar(Integer IdUsuario, UsuarioDTO dto) {
        repository.buscarPorIdUsuarioSQL(IdUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        repository.buscarPorEmailSQL(dto.getEmail()).ifPresent(otro -> {
            if (!otro.getIdUsuario().equals(IdUsuario)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "El email ya existe");
            }
        });

        int filas = repository.actualizarUsuario(
                IdUsuario,
                dto.getNombre(),
                dto.getUserName(),
                dto.getEmail(),
                dto.getPassword());

        if (filas == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        return mapper.toDTO(
                repository.buscarPorIdUsuarioSQL(IdUsuario).orElseThrow());
    }

    @Override
    @Transactional
    public void eliminar(Integer IdUsuario) {
        int filas = repository.eliminarUsuario(IdUsuario);

        if (filas == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }
}
