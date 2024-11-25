package com.example.temaa3.actividad3_9;

import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        int puerto = 4444;
        byte[] buffer = new byte[1024];

        try {
            socket = new DatagramSocket(puerto);
            System.out.println("Servidor iniciado...");

            while (true) {
                // Recibir el paquete de un cliente
                DatagramPacket paqueteRecepcion = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecepcion);

                // Crear un hilo para atender al cliente
                new Thread(new ClienteHandler(socket, paqueteRecepcion)).start();
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    // Clase interna que maneja a cada cliente en un hilo
    static class ClienteHandler implements Runnable {
        private DatagramSocket socket;
        private DatagramPacket paqueteRecepcion;

        public ClienteHandler(DatagramSocket socket, DatagramPacket paqueteRecepcion) {
            this.socket = socket;
            this.paqueteRecepcion = paqueteRecepcion;
        }

        @Override
        public void run() {
            try {
                InetAddress clienteIP = paqueteRecepcion.getAddress();
                int clientePuerto = paqueteRecepcion.getPort();
                String mensajeRecibido = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());

                // Mostrar la información del cliente en el servidor
                System.out.println("=>Conecta IP " + clienteIP + ", Puerto remoto: " + clientePuerto);

                // Si el mensaje es '*', desconectar cliente
                if (mensajeRecibido.equals("*")) {
                    System.out.println("=>Desconecta IP " + clienteIP + ", Puerto remoto: " + clientePuerto);
                    return;  // Finaliza el hilo
                }

                // Convertir el mensaje a mayúsculas
                String mensajeMayusculas = mensajeRecibido.toUpperCase();

                // Enviar el mensaje convertido al cliente
                byte[] datosRespuesta = mensajeMayusculas.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosRespuesta, datosRespuesta.length, clienteIP, clientePuerto);
                socket.send(paqueteEnvio);
            } catch (IOException e) {
                System.out.println("Error al atender cliente: " + e.getMessage());
            }
        }
    }
}
