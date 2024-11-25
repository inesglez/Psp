package com.example.temaa3.actividad3_8;

import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        InetAddress servidor;
        int puerto = 9876;
        byte[] buffer;

        try {
            socket = new DatagramSocket();
            servidor = InetAddress.getByName("localhost");

            // Crear objeto Persona
            Persona persona = new Persona("Juan", 25);

            // Mostrar datos del objeto Persona que se va a enviar
            System.out.println("Cliente enviando: " + persona);

            // Serializar el objeto Persona
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(persona);
            objectOutputStream.flush();

            buffer = byteArrayOutputStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, servidor, puerto);
            socket.send(packet);  // Enviar el paquete al servidor

            // Recibir la respuesta del servidor
            buffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            // Deserializar el objeto Persona recibido
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(responsePacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Persona personaModificada = (Persona) objectInputStream.readObject();

            // Mostrar los datos del objeto Persona recibido
            System.out.println("Cliente recibió: " + personaModificada);

            // Cerrar el socket
            socket.close();
        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
