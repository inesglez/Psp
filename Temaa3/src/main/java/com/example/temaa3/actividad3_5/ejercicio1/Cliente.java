package com.example.temaa3.actividad3_5.ejercicio1;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        String direccionServidor = "localhost";
        int puerto = 12345;

        try (Socket socket = new Socket(direccionServidor, puerto)) {
            // Crear flujos de entrada y salida
            BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salidaServidor = new PrintWriter(socket.getOutputStream(), true);

            String mensaje;
            while (true) {
                // Leer mensaje del teclado
                System.out.print("Introduce una cadena (o '*' para salir): ");
                mensaje = entradaTeclado.readLine();

                // Enviar el mensaje al servidor
                salidaServidor.println(mensaje);

                // Si el mensaje es '*', terminar
                if (mensaje.equals("*")) {
                    break;
                }

                // Leer y mostrar la respuesta del servidor
                String respuesta = entradaServidor.readLine();
                System.out.println(respuesta);
            }
        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
