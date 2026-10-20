package com.proyectoraul.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {
    private Integer id_usuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 100, message = "El nombre de usuario no puede superar 100 caracteres")
    private String userName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150, message = "El email no puede superar 150 caracteres")
    private String email;

    @NotNull(message = "La edad es obligatoria")
    @Size(min = 8, message = "Hay que poner una contraseña con el menos 8 caracteres")
    private String password;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id_usuario, String nombre, String userName, String email, String password) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id_usuario;
    }

    public void setId(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
