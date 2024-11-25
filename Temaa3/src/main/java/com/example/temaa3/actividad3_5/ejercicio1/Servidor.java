package com.example.temaa3.actividad3_5.ejercicio1;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 12345;
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado, esperando cliente...");

            // Aceptar la conexión del cliente
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress());

            // Crear flujos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

            String mensaje;
            while (true) {
                // Leer el mensaje del cliente
                mensaje = entrada.readLine();
                if (mensaje == null) {
                    break;
                }

                System.out.println("Mensaje recibido del cliente: " + mensaje);

                // Si el mensaje es "*", finalizar el servidor
                if (mensaje.equals("*")) {
                    System.out.println("Servidor finalizado.");
                    break;
                }

                // Enviar el número de caracteres de la cadena recibida
                int longitud = mensaje.length();
                salida.println("Número de caracteres: " + longitud);
            }

            // Cerrar la conexión con el cliente
            cliente.close();
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
