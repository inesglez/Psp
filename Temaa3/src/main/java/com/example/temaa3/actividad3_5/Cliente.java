package com.example.temaa3.actividad3_5;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        String servidorIP = "127.0.0.1";  // IP del servidor (localhost para este ejemplo)
        int puerto = 12345;  // El puerto en el que el servidor escucha

        try (Socket socket = new Socket(servidorIP, puerto)) {
            // Crear los flujos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Leer el mensaje enviado por el servidor
            String mensajeServidor = entrada.readLine();
            System.out.println("Mensaje recibido del servidor: " + mensajeServidor);

        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
