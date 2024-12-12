package com.example.tema3.actividad3_6;
import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String[] args) {
        int puerto = 6000;  // Puerto donde el servidor escuchará
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(puerto);
            System.out.println("Servidor UDP iniciado en el puerto " + puerto);

            byte[] buffer = new byte[1024];

            // Bucle para recibir y enviar mensajes
            while (true) {
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecibido);  // Recibe el paquete del cliente

                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                System.out.println("Mensaje recibido del cliente: " + mensaje);

                // Si el mensaje es un asterisco, terminar el servidor
                if (mensaje.equals("*")) {
                    System.out.println("Servidor finalizado.");
                    break;
                }

                // Convertir el mensaje a mayúsculas
                String mensajeMayusculas = mensaje.toUpperCase();

                // Enviar el mensaje en mayúsculas de vuelta al cliente
                InetAddress direccionCliente = paqueteRecibido.getAddress();
                int puertoCliente = paqueteRecibido.getPort();
                DatagramPacket paqueteEnvio = new DatagramPacket(mensajeMayusculas.getBytes(), mensajeMayusculas.length(), direccionCliente, puertoCliente);
                socket.send(paqueteEnvio);
            }

            // Cerrar socket
            System.out.println("Cerrando conexión...");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}