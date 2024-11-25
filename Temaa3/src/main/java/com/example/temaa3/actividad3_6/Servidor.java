package com.example.temaa3.actividad3_6;

import java.io.IOException;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        int puerto = 9876;  // Puerto en el que escucha el servidor
        byte[] buffer = new byte[1024];

        try {
            // Crear el socket UDP para escuchar en el puerto especificado
            socket = new DatagramSocket(puerto);
            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                // Recibir paquete del cliente
                DatagramPacket paqueteRecepcion = new DatagramPacket(buffer, buffer.length);
                System.out.println("Esperando recibir datos del cliente...");
                socket.receive(paqueteRecepcion);  // Bloquea hasta recibir datos
                System.out.println("Paquete recibido");

                // Convertir los datos recibidos en una cadena
                String mensajeRecibido = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
                System.out.println("Cliente: " + mensajeRecibido);

                // Si el mensaje es un asterisco, finalizar el servidor
                if (mensajeRecibido.equals("*")) {
                    System.out.println("Finalizando servidor...");
                    break;
                }

                // Convertir el mensaje a mayúsculas
                String mensajeMayusculas = mensajeRecibido.toUpperCase();

                // Preparar el paquete para enviar la respuesta al cliente
                InetAddress clienteIP = paqueteRecepcion.getAddress();
                int clientePuerto = paqueteRecepcion.getPort();
                byte[] datosRespuesta = mensajeMayusculas.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosRespuesta, datosRespuesta.length, clienteIP, clientePuerto);

                System.out.println("Enviando respuesta al cliente...");
                socket.send(paqueteEnvio);  // Enviar la respuesta
                System.out.println("Respuesta enviada: " + mensajeMayusculas);
            }

        } catch (SocketException e) {
            System.out.println("Error en el socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de comunicación: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();  // Cerrar el socket al final
                System.out.println("Socket cerrado");
            }
        }
    }
}
