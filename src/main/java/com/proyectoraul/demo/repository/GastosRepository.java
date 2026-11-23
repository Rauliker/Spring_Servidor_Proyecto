package com.proyectoraul.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyectoraul.demo.model.Gastos;

public interface GastosRepository extends JpaRepository<Gastos, Integer> {

    List<Gastos> findByPiso_IdPisoOrderByFechaDesc(Integer idPiso);

    @Modifying
    @Query("""
            delete from Gastos g
            where g.piso.idPiso = :idPiso
              and (g.usuarioPagador.idUsuario = :idUsuario
                   or g.usuarioCreador.idUsuario = :idUsuario)
            """)
    int deleteRelacionadosConUsuarioEnPiso(
            @Param("idPiso") Integer idPiso,
            @Param("idUsuario") Integer idUsuario);
}
