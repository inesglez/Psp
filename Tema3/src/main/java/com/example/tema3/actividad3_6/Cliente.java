package com.example.tema3.actividad3_6;

import java.io.*;
import java.net.*;

public class Cliente {

    public static void main(String[] args) {
        String host = "localhost";  // Dirección del servidor
        int puerto = 6000;  // Puerto del servidor
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(5000);  // Establece el tiempo de espera en 5000 milisegundos

            BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Introduce un mensaje (asterisco para salir): ");
                String mensaje = entradaTeclado.readLine();

                // Si el mensaje es un asterisco, terminar
                if (mensaje.equals("*")) {
                    break;
                }

                // Enviar el mensaje al servidor
                InetAddress direccionServidor = InetAddress.getByName(host);
                DatagramPacket paqueteEnvio = new DatagramPacket(mensaje.getBytes(), mensaje.length(), direccionServidor, puerto);
                socket.send(paqueteEnvio);

                // Recibir la respuesta del servidor
                DatagramPacket paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
                try {
                    socket.receive(paqueteRecibido);  // Espera la respuesta del servidor
                    String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                    System.out.println("Respuesta del servidor: " + respuesta);
                } catch (InterruptedIOException e) {
                    // Salta una vez han pasado los 5000 ms y el servidor no haya tenido respuesta del Cliente
                    System.out.println("El paquete se ha perdido, no se recibió respuesta del servidor.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

}