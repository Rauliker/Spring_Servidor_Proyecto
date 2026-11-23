package com.proyectoraul.demo.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

class UsuarioDTOTest {

    @Test
    void passwordEsSoloDeEntradaParaNoExponerlaEnRespuestas() throws Exception {
        JsonProperty annotation = UsuarioDTO.class
                .getDeclaredField("password")
                .getAnnotation(JsonProperty.class);

        assertEquals(JsonProperty.Access.WRITE_ONLY, annotation.access());
    }
}
