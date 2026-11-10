package com.proyectoraul.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.model.Usuario;

@Service
public class MailNotificationService {

    private final JavaMailSender mailSender;
    private final String from;

    public MailNotificationService(
            JavaMailSender mailSender,
            @Value("${app.mail.from}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    public void enviarAvisoInicioSesion(Usuario usuario) {
        enviar(
                usuario.getEmail(),
                "Inicio de sesión",
                "Hola " + usuario.getNombre()
                        + ", se ha iniciado sesión en tu cuenta.");
    }

    public void enviarAvisoAltaEnPiso(Usuario usuario, Piso piso) {
        enviar(
                usuario.getEmail(),
                "Te has unido a un piso",
                "Hola " + usuario.getNombre()
                        + ", te has unido al piso \"" + piso.getNombre() + "\".");
    }

    public void enviarAvisoSalidaDePiso(Usuario usuario, Piso piso) {
        enviar(
                usuario.getEmail(),
                "Has salido de un piso",
                "Hola " + usuario.getNombre()
                        + ", has salido del piso \"" + piso.getNombre() + "\".");
    }

    private void enviar(String destinatario, String asunto, String texto) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(from);
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(texto);
        mailSender.send(mensaje);
    }
}
