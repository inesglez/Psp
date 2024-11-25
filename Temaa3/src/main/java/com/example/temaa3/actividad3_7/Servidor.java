package com.example.temaa3.actividad3_7;

import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        int puerto = 9876;
        byte[] buffer = new byte[1024];

        try {
            socket = new DatagramSocket(puerto);
            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                // Recibir paquete del cliente
                DatagramPacket paqueteRecepcion = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecepcion);

                // Deserializar el objeto Numeros
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paqueteRecepcion.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Numeros numeros = (Numeros) objectInputStream.readObject();

                // Si el número es menor o igual a 0, finalizar el servidor
                if (numeros.getNumero() <= 0) {
                    System.out.println("Finalizando servidor...");
                    break;
                }

                // Enviar el objeto Numeros de vuelta al cliente
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(numeros);
                objectOutputStream.flush();

                byte[] datosRespuesta = byteArrayOutputStream.toByteArray();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosRespuesta, datosRespuesta.length, paqueteRecepcion.getAddress(), paqueteRecepcion.getPort());
                socket.send(paqueteEnvio);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
