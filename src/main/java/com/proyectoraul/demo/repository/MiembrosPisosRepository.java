package com.proyectoraul.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoraul.demo.model.MiembrosPiso;
import com.proyectoraul.demo.model.Role;

public interface MiembrosPisosRepository
                extends JpaRepository<MiembrosPiso, Long> {

        // Buscar los miembros de un piso
        List<MiembrosPiso> findByPiso_IdPiso(Long idPiso);

        // Buscar los pisos en los que participa un usuario
        List<MiembrosPiso> findByUsuario_IdUsuario(Long idUsuario);

        // Buscar la relación entre un usuario y un piso
        Optional<MiembrosPiso> findByUsuario_IdUsuarioAndPiso_IdPiso(
                        Long idUsuario, Long idPiso);

        // Comprobar si un usuario ya pertenece a un piso
        boolean existsByUsuario_IdUsuarioAndPiso_IdPiso(
                        Long idUsuario, Long idPiso);

        // Buscar miembros de un piso según su rol
        List<MiembrosPiso> findByPiso_IdPisoAndRol(
                        Long idPiso, Role rol);
}
