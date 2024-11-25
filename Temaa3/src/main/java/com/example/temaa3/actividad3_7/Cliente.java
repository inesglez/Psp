package com.example.temaa3.actividad3_7;

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

            // Pedir número al usuario
            System.out.print("Introduce un número: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int numero = Integer.parseInt(reader.readLine());

            // Crear objeto Numeros y enviarlo al servidor
            Numeros numeros = new Numeros(numero);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(numeros);
            objectOutputStream.flush();

            buffer = byteArrayOutputStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, servidor, puerto);
            socket.send(packet);

            // Recibir la respuesta del servidor
            buffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            // Deserializar el objeto Numeros recibido
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(responsePacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Numeros respuesta = (Numeros) objectInputStream.readObject();

            System.out.println("Número: " + respuesta.getNumero());
            System.out.println("Cuadrado: " + respuesta.getCuadrado());
            System.out.println("Cubo: " + respuesta.getCubo());

            // Cerrar el socket
            socket.close();
        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
