package com.example.tema3_2.Actividad3_8;

import java.io.*;
import java.net.*;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            // Crear el socket del cliente
            DatagramSocket socket = new DatagramSocket();
            InetAddress direccionServidor = InetAddress.getByName("localhost");  // Dirección del servidor
            int puertoServidor = 12345;  // Puerto donde el servidor está escuchando

            // Crear un objeto Persona y serializarlo
            Persona persona = new Persona("Juan", "Pérez", 25);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(persona);  // Serializar el objeto Persona
            oos.flush();

            // Crear un paquete con los datos serializados y enviarlo al servidor
            byte[] datos = baos.toByteArray();
            DatagramPacket paqueteEnviado = new DatagramPacket(datos, datos.length, direccionServidor, puertoServidor);
            socket.send(paqueteEnviado);
            System.out.println("Cliente envió: " + persona);

            // Preparar buffer para recibir el datagrama de respuesta
            byte[] buffer = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);

            // para recibir respuesta del servidor
            socket.receive(paqueteRecibido);

            // Deserializar el objeto Persona recibido
            ByteArrayInputStream bais = new ByteArrayInputStream(paqueteRecibido.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Persona personaModificada = (Persona) ois.readObject();  // Deserializar la respuesta
            System.out.println("Cliente recibió: " + personaModificada);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
