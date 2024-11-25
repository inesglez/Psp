package com.example.temaa3.actividad3_8.actividad3_7;

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

            // Solicitar número al usuario
            System.out.print("Introduce un número: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int numero = Integer.parseInt(reader.readLine());

            // Crear objeto Numeros con el número introducido
            Numeros numeros = new Numeros(numero);

            // Mostrar los datos del objeto Numeros que se va a enviar
            System.out.println("Cliente enviando: " + numeros);

            // Serializar el objeto Numeros
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(numeros);
            objectOutputStream.flush();

            buffer = byteArrayOutputStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, servidor, puerto);
            socket.send(packet);  // Enviar el paquete al servidor

            // Recibir la respuesta del servidor
            buffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            // Deserializar el objeto Numeros recibido
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(responsePacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Numeros numerosModificados = (Numeros) objectInputStream.readObject();

            // Mostrar los datos del objeto Numeros recibido
            System.out.println("Cliente recibió: " + numerosModificados);

            // Cerrar el socket
            socket.close();
        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
