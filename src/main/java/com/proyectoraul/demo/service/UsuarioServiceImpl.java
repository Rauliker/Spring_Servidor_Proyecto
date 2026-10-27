
package com.proyectoraul.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(
            UsuarioRepository repository,
            UsuarioMapper mapper,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
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

        // El ID lo genera PostgreSQL.
        usuario.setIdUsuario(null);

        // Codificar la contraseña antes de guardarla.
        usuario.setPassword(
                passwordEncoder.encode(dto.getPassword()));

        // Guardar el usuario con la contraseña protegida.
        Usuario guardado = repository.save(usuario);

        // Devolver el usuario guardado, con su ID.
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
    public UsuarioDTO buscarPorIdUsuario(Integer idUsuario) {
        Usuario usuario = repository.buscarPorIdUsuarioSQL(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"));

        return mapper.toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizar(Integer idUsuario, UsuarioDTO dto) {

        Usuario existente = repository.buscarPorIdUsuarioSQL(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"));

        repository.buscarPorEmailSQL(dto.getEmail()).ifPresent(otro -> {
            if (!otro.getIdUsuario().equals(idUsuario)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "El email ya existe");
            }
        });

        // Si no se proporciona contraseña, conservar la actual.
        String passwordActualizada = dto.getPassword() == null
                || dto.getPassword().isBlank()
                        ? existente.getPassword()
                        : passwordEncoder.encode(dto.getPassword());

        int filas = repository.actualizarUsuario(
                idUsuario,
                dto.getNombre(),
                dto.getUserName(),
                dto.getEmail(),
                passwordActualizada);

        if (filas == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado");
        }

        return mapper.toDTO(
                repository.buscarPorIdUsuarioSQL(idUsuario)
                        .orElseThrow());
    }

    @Override
    @Transactional
    public void eliminar(Integer idUsuario) {
        int filas = repository.eliminarUsuario(idUsuario);

        if (filas == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado");
        }
    }
}
