package com.example.tema3_2.Actividad3_8;
import java.io.*;
import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) {
        try {
            // Crear el socket en el puerto 12345 para escuchar conexiones
            DatagramSocket socket = new DatagramSocket(12345);
            System.out.println("Servidor UDP esperando datagrama...");

            // Preparar un buffer para recibir datos del cliente
            byte[] buffer = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);

            // Recibir el datagrama del cliente
            socket.receive(paqueteRecibido);

            // Deserializar el objeto Persona recibido
            ByteArrayInputStream bais = new ByteArrayInputStream(paqueteRecibido.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Persona persona = (Persona) ois.readObject();
            System.out.println("Servidor recibió: " + persona);

            // Modificar los datos del objeto Persona
            persona.setNombre(persona.getNombre() + " Modificado");  // Modifica el nombre
            persona.setEdad(persona.getEdad() + 10);                 // Aumenta la edad

            // Serializar el objeto modificado para enviarlo al cliente
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(persona);
            oos.flush();

            // Preparar y enviar el datagrama con los datos modificados
            byte[] datosModificados = baos.toByteArray();
            InetAddress direccionCliente = paqueteRecibido.getAddress();  // Dirección del cliente
            int puertoCliente = paqueteRecibido.getPort();                // Puerto del cliente

            DatagramPacket paqueteEnviado = new DatagramPacket(datosModificados, datosModificados.length, direccionCliente, puertoCliente);
            socket.send(paqueteEnviado);

            System.out.println("Objeto modificado enviado al cliente.");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
