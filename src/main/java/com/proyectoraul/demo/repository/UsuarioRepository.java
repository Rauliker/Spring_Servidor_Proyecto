
package com.proyectoraul.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyectoraul.demo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT * FROM usuarios ORDER BY id_usuario", nativeQuery = true)
    List<Usuario> listarUsuarios();

    @Query(value = "SELECT * FROM usuarios WHERE id_usuario = :id_usuario", nativeQuery = true)
    Optional<Usuario> buscarPorIdSQL(@Param("id_usuario") Integer id_usuario);

    @Query(value = "SELECT * FROM usuarios WHERE email = :email", nativeQuery = true)
    Optional<Usuario> buscarPorEmailSQL(@Param("email") String email);

    @Modifying
    @Query(value = """
            UPDATE usuarios
            SET nombre = :nombre,
                userName = :userName,
                email = :email,
                password = :password
            WHERE id_usuario = :id_usuario
            """, nativeQuery = true)
    int actualizarUsuario(
            @Param("id_usuario") Integer id_usuario,
            @Param("nombre") String nombre,
            @Param("userName") String userName,
            @Param("email") String email,
            @Param("password") String password);

    @Modifying
    @Query(value = "DELETE FROM usuarios WHERE id_usuario = :id_usuario", nativeQuery = true)
    int eliminarUsuario(@Param("id_usuario") Integer id_usuario);

    boolean existsByEmail(String email);
}
