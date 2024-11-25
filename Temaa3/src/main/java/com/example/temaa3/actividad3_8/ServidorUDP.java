package com.example.temaa3.actividad3_8;

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

                // Deserializar el objeto Persona
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paqueteRecepcion.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Persona personaRecibida = (Persona) objectInputStream.readObject();

                // Mostrar los datos de la Persona recibida
                System.out.println("Servidor recibió: " + personaRecibida);

                // Modificar los datos del objeto Persona
                personaRecibida.setNombre(personaRecibida.getNombre() + " Modificado");
                personaRecibida.setEdad(personaRecibida.getEdad() + 1);  // Incrementar edad

                // Mostrar los datos modificados
                System.out.println("Servidor modificó: " + personaRecibida);

                // Serializar el objeto Persona modificado
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(personaRecibida);
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
