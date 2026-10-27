package com.proyectoraul.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyectoraul.demo.model.Piso;

public interface PisoRepository extends JpaRepository<Piso, Integer> {
        @Query(value = "SELECT * FROM pisos ORDER BY id_piso", nativeQuery = true)
        List<Piso> listarPisos();

        @Query(value = "SELECT * FROM pisos WHERE id_piso = :id_piso", nativeQuery = true)
        Optional<Piso> buscarPorIdPisoSQL(@Param("id_piso") Integer id_piso);

        @Query(value = "SELECT * FROM pisos WHERE codigo_invitacion = :codigo_invitacion", nativeQuery = true)
        Optional<Piso> buscarPorCodigoPisoSQL(@Param("codigo_invitacion") String codigo_invitacion);

        @Modifying
        @Query(value = """
                        UPDATE pisos
                        SET nombre = :nombre
                        WHERE id_piso = :id_piso
                        """, nativeQuery = true)
        int actualizarPiso(
                        @Param("id_piso") Integer id_piso,
                        @Param("nombre") String nombre);

        @Modifying
        @Query(value = "DELETE FROM pisos WHERE id_piso = :id_piso", nativeQuery = true)
        int eliminarPiso(@Param("id_piso") Integer id_piso);

        boolean existsByCodigoInvitacion(String codigo_invitacion);
}
