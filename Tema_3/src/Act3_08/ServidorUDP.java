package Act3_08;

import java.io.*;
import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) {
        int puerto = 6000;

        try {
            DatagramSocket socketServidor = new DatagramSocket(puerto);
            System.out.println("Servidor UDP esperando datagramas en el puerto 6000...");

            byte[] bufferEntrada = new byte[1024];
            byte[] bufferSalida;

            while (true) {
                try {
                    // Recibir datagrama del cliente
                    DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                    socketServidor.receive(paqueteEntrada);

                    // Convertir bytes a objeto Persona
                    ByteArrayInputStream bais = new ByteArrayInputStream(paqueteEntrada.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Persona persona = (Persona) ois.readObject();
                    System.out.println("Objeto recibido del cliente: " + persona);

                    // Modificar objeto Persona
                    persona.setNombre(persona.getNombre().toUpperCase());
                    persona.setEdad(persona.getEdad() + 5);

                    // Enviar objeto modificado de vuelta al cliente
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(persona);
                    bufferSalida = baos.toByteArray();

                    InetAddress direccionCliente = paqueteEntrada.getAddress();
                    int puertoCliente = paqueteEntrada.getPort();
                    DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionCliente, puertoCliente);
                    socketServidor.send(paqueteSalida);

                    System.out.println("Objeto modificado enviado al cliente: " + persona);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error procesando datagrama: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
