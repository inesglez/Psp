package com.example.temaa3.actividad3_8.actividad3_7;

import java.net.*;
import java.io.*;

public class ServidorUDP {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        int puerto = 9876;
        byte[] buffer = new byte[1024];

        try {
            socket = new DatagramSocket(puerto);
            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                // Recibir el paquete del cliente
                DatagramPacket paqueteRecepcion = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecepcion);

                // Deserializar el objeto Numeros
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paqueteRecepcion.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Numeros numerosRecibidos = (Numeros) objectInputStream.readObject();

                // Mostrar los datos de los Numeros recibidos
                System.out.println("Servidor recibió: " + numerosRecibidos);

                // Si el número recibido es menor o igual a 0, finalizamos el servidor
                if (numerosRecibidos.getNumero() <= 0) {
                    System.out.println("Finalizando servidor...");
                    break;
                }

                // Serializar el objeto Numeros modificado
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(numerosRecibidos);
                objectOutputStream.flush();

                byte[] datosRespuesta = byteArrayOutputStream.toByteArray();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosRespuesta, datosRespuesta.length,
                        paqueteRecepcion.getAddress(), paqueteRecepcion.getPort());
                socket.send(paqueteEnvio);  // Enviar el objeto modificado de vuelta al cliente
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
