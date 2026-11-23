package com.proyectoraul.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyectoraul.demo.model.Tareas;

public interface TareasRepository extends JpaRepository<Tareas, Integer> {

    List<Tareas> findByPiso_IdPisoOrderByFechaLimiteAsc(Integer idPiso);

    List<Tareas> findByUsuarioAsignado_IdUsuarioOrderByFechaLimiteAsc(Integer idUsuario);

    @Modifying
    @Query("""
            delete from Tareas t
            where t.piso.idPiso = :idPiso
              and (t.usuarioAsignado.idUsuario = :idUsuario
                   or t.usuarioCreador.idUsuario = :idUsuario)
            """)
    int deleteRelacionadasConUsuarioEnPiso(
            @Param("idPiso") Integer idPiso,
            @Param("idUsuario") Integer idUsuario);
}
