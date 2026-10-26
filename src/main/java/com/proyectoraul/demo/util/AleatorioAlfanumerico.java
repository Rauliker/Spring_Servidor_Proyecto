package com.proyectoraul.demo.util;

import java.util.Random;

public class AleatorioAlfanumerico {

    public String ValoresAleatorios(int numCaracteres) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder cadena = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < numCaracteres; i++) {
            cadena.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        return cadena.toString();
    }
}