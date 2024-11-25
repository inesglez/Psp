package com.example.temaa3.actividad3_9;

import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        InetAddress servidor;
        int puerto = 4444;
        byte[] buffer;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            socket = new DatagramSocket();
            servidor = InetAddress.getByName("localhost");

            while (true) {
                // Solicitar al usuario ingresar un mensaje
                System.out.print("Introduce una cadena (o '*' para salir): ");
                String mensaje = reader.readLine();

                // Si el mensaje es '*', salir
                if (mensaje.equals("*")) {
                    socket.send(new DatagramPacket(mensaje.getBytes(), mensaje.length(), servidor, puerto));
                    break;
                }

                // Enviar mensaje al servidor
                buffer = mensaje.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, servidor, puerto);
                socket.send(packet);

                // Recibir respuesta del servidor
                buffer = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(responsePacket);

                // Mostrar la respuesta en mayúsculas
                String respuesta = new String(responsePacket.getData(), 0, responsePacket.getLength());
                System.out.println("Servidor (mayúsculas): " + respuesta);
            }

        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
