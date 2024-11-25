package com.example.temaa3.actividad3_5;

import java.io.*;
import java.net.*;

public class ServidorN {
    public static void main(String[] args) {
        int puerto = 12345;  // Puerto en el que el servidor escucha
        int maxClientes = 5;  // Número máximo de clientes a atender (puedes cambiar este valor a N)

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor en espera de clientes en el puerto " + puerto);

            // Aceptar hasta maxClientes conexiones
            for (int i = 1; i <= maxClientes; i++) {
                // Esperar a que el cliente se conecte
                try (Socket socketCliente = serverSocket.accept()) {
                    System.out.println("Cliente " + i + " conectado desde: " + socketCliente.getInetAddress().getHostAddress());

                    // Crear los flujos de entrada y salida
                    PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

                    // Enviar el mensaje al cliente indicando su número
                    String mensaje = "Eres el cliente número: " + i;
                    salida.println(mensaje);
                    System.out.println("Enviando al cliente " + i + ": " + mensaje);
                } catch (IOException e) {
                    System.out.println("Error al atender al cliente " + i + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
