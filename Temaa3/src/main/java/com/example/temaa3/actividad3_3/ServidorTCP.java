package com.example.temaa3.actividad3_3;

import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) {
        int puerto = 12345;  // Puerto en el que el servidor escucha

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor en espera de clientes en el puerto " + puerto);

            // Esperar a que el cliente se conecte
            try (Socket socketCliente = serverSocket.accept()) {
                System.out.println("Cliente conectado desde: " + socketCliente.getInetAddress().getHostAddress());

                // Crear los flujos de entrada y salida
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

                // Enviar mensaje al cliente
                String mensaje = "¡Hola, Cliente!";
                System.out.println("Enviando al cliente: " + mensaje);
                salida.println(mensaje);

                // Recibir respuesta del cliente
                String respuesta = entrada.readLine();
                System.out.println("Mensaje recibido del cliente: " + respuesta);
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
