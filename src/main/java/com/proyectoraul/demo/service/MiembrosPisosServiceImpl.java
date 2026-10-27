
package com.proyectoraul.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectoraul.demo.dto.MiembrosPisosDTO;
import com.proyectoraul.demo.mapper.MiembrosPisosMapper;
import com.proyectoraul.demo.model.MiembrosPiso;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Usuario;
import com.proyectoraul.demo.repository.MiembrosPisosRepository;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.repository.UsuarioRepository;

@Service
@Transactional
public class MiembrosPisosServiceImpl implements MiembrosPisosService {

    private final MiembrosPisosRepository miembrosRepository;
    private final UsuarioRepository usuarioRepository;
    private final PisoRepository pisoRepository;

    public MiembrosPisosServiceImpl(
            MiembrosPisosRepository miembrosRepository,
            UsuarioRepository usuarioRepository,
            PisoRepository pisoRepository) {
        this.miembrosRepository = miembrosRepository;
        this.usuarioRepository = usuarioRepository;
        this.pisoRepository = pisoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MiembrosPisosDTO> findAll() {
        return miembrosRepository.findAll()
                .stream()
                .map(MiembrosPisosMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MiembrosPisosDTO findById(Long id) {
        MiembrosPiso miembro = miembrosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "No existe la membresía con ID: " + id));

        return MiembrosPisosMapper.toDTO(miembro);
    }

    @Override
    public MiembrosPisosDTO create(MiembrosPisosDTO dto) {
        if (miembrosRepository
                .existsByUsuario_IdUsuarioAndPiso_IdPiso(
                        dto.getIdUsuario(), dto.getIdPiso())) {
            throw new RuntimeException(
                    "El usuario ya pertenece a este piso");
        }
        Usuario usuario = usuarioRepository
                .findById(dto.getIdUsuario().intValue())
                .orElseThrow(() -> new RuntimeException("No existe el usuario"));

        Piso piso = pisoRepository
                .findById(dto.getIdPiso().intValue())
                .orElseThrow(() -> new RuntimeException("No existe el piso"));

        MiembrosPiso miembro = MiembrosPisosMapper.toEntity(dto, usuario, piso);

        miembro.setIdMiembro(null);

        MiembrosPiso guardado = miembrosRepository.save(miembro);

        return MiembrosPisosMapper.toDTO(guardado);
    }

    @Override
    public MiembrosPisosDTO update(Long id, MiembrosPisosDTO dto) {
        MiembrosPiso miembro = miembrosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "No existe la membresía con ID: " + id));

        Usuario usuario = usuarioRepository
                .findById(dto.getIdUsuario().intValue())
                .orElseThrow(() -> new RuntimeException("No existe el usuario"));

        Piso piso = pisoRepository
                .findById(dto.getIdPiso().intValue())
                .orElseThrow(() -> new RuntimeException("No existe el piso"));

        boolean existe = miembrosRepository
                .existsByUsuario_IdUsuarioAndPiso_IdPiso(
                        dto.getIdUsuario(), dto.getIdPiso());

        boolean mismaRelacion = miembro.getUsuario().getIdUsuario()
                .equals(dto.getIdUsuario())
                && miembro.getPiso().getIdPiso()
                        .equals(dto.getIdPiso());

        if (existe && !mismaRelacion) {
            throw new RuntimeException(
                    "El usuario ya pertenece a este piso");
        }

        miembro.setUsuario(usuario);
        miembro.setPiso(piso);
        miembro.setRol(dto.getRol());

        MiembrosPiso actualizado = miembrosRepository.save(miembro);

        return MiembrosPisosMapper.toDTO(actualizado);
    }

    @Override
    public void delete(Long id) {
        MiembrosPiso miembro = miembrosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "No existe la membresía con ID: " + id));

        miembrosRepository.delete(miembro);
    }
}
