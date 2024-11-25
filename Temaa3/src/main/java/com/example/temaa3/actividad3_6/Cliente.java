package com.example.temaa3.actividad3_6;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String mensaje;
        InetAddress servidorIP;
        int puerto = 9876;  // Puerto del servidor
        int timeout = 5000; // Tiempo de espera en milisegundos (5 segundos)

        try {
            // Crear el socket UDP
            socket = new DatagramSocket();

            // Dirección del servidor (localhost en este caso)
            servidorIP = InetAddress.getByName("localhost");

            System.out.println("Escribe un mensaje (introduce '*' para finalizar):");

            while (true) {
                // Leer el mensaje del usuario
                mensaje = entrada.readLine();

                // Si el mensaje es un asterisco, terminar
                if (mensaje.equals("*")) {
                    break;
                }

                // Enviar el mensaje al servidor
                byte[] datos = mensaje.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(datos, datos.length, servidorIP, puerto);
                socket.send(paqueteEnvio);

                // Configurar el tiempo de espera para recibir respuesta
                socket.setSoTimeout(timeout);

                try {
                    // Recibir la respuesta del servidor
                    byte[] buffer = new byte[1024];
                    DatagramPacket paqueteRecepcion = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paqueteRecepcion);  // Este método bloquea hasta que reciba datos

                    // Mostrar la respuesta del servidor
                    String respuesta = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
                    System.out.println("Servidor: " + respuesta);

                } catch (InterruptedIOException e) {
                    // Si se supera el tiempo de espera, mostrar mensaje de error
                    System.out.println("Paquete perdido, no se recibió respuesta del servidor.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error de comunicación: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();  // Cerrar el socket al final
            }
        }
    }
}
