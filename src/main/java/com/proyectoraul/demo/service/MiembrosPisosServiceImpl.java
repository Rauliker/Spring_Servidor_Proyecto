
package com.proyectoraul.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectoraul.demo.dto.MiembrosPisosDTO;
import com.proyectoraul.demo.mapper.MiembrosPisosMapper;
import com.proyectoraul.demo.model.MiembrosPiso;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Role;
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
    public MiembrosPisosDTO create(
            Integer idUsuario, String codigoInvitacion, Role rol) {
        if (idUsuario == null) {
            throw new IllegalArgumentException(
                    "El token no contiene el ID del usuario");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException(
                        "No existe el usuario"));

        Piso piso = pisoRepository
                .buscarPorCodigoPisoSQL(codigoInvitacion)
                .orElseThrow(() -> new RuntimeException(
                        "No existe el piso con ese código"));

        Long idUsuarioLong = usuario.getIdUsuario().longValue();
        Long idPisoLong = piso.getIdPiso().longValue();
        boolean yaEsMiembro = miembrosRepository
                .existsByUsuario_IdUsuarioAndPiso_IdPiso(
                        idUsuarioLong, idPisoLong);
        if (yaEsMiembro) {
            throw new RuntimeException("El usuario ya pertenece a este piso");
        }

        Role rolAsignado = Objects.equals(
                usuario.getIdUsuario(),
                piso.getUsuarioCreador().getIdUsuario())
                        ? Role.ADMIN
                        : Role.MIEMBRO;

        MiembrosPiso miembro = MiembrosPisosMapper.toEntity(
                rolAsignado, usuario, piso);
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

        boolean mismaRelacion = miembro.getUsuario().getIdUsuario().longValue()
                == dto.getIdUsuario()
                && miembro.getPiso().getIdPiso().longValue()
                        == dto.getIdPiso();

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
