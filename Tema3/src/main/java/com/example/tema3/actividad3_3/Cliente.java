package com.example.tema3.actividad3_3;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost"; // Dirección del servidor
        int puerto = 6000; // Puerto del servidor remoto

        System.out.println("Programa cliente iniciado...");

        try (Socket cliente = new Socket(host, puerto)) {
            DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());

            // Enviar mensaje al servidor
            flujoSalida.writeUTF("¡Hola, servidor!");

            // Creación de flujo de entrada para recibir mensaje del servidor
            DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());

            // El servidor me envía un mensaje
            String mensajeServidor = flujoEntrada.readUTF();
            System.out.println("Recibiendo mensaje del servidor: \n\t" + mensajeServidor);

            // Convertir el mensaje recibido a minúsculas
            String mensajeEnMinusculas = mensajeServidor.toLowerCase();

            // Enviar el mensaje convertido al servidor
            flujoSalida.writeUTF(mensajeEnMinusculas);
            System.out.println("Enviando mensaje en minúsculas al servidor: \n\t" + mensajeEnMinusculas);

            // Cerrar flujos y socket
            flujoSalida.close();
            flujoEntrada.close();
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}