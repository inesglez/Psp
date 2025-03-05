package Act3_08;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 6000;

        try {
            DatagramSocket socketCliente = new DatagramSocket();
            InetAddress direccionServidor = InetAddress.getByName(host);

            Scanner scanner = new Scanner(System.in);

            // Solicitar datos de la persona al usuario
            System.out.print("Introduce el nombre de la persona: ");
            String nombre = scanner.nextLine();
            System.out.print("Introduce la edad de la persona: ");
            int edad = scanner.nextInt();

            Persona persona = new Persona(nombre, edad);
            System.out.println("Objeto a enviar al servidor: " + persona);

            // Enviar objeto Persona al servidor
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(persona);
            byte[] bufferSalida = baos.toByteArray();

            DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionServidor, puerto);
            socketCliente.send(paqueteSalida);

            // Recibir objeto modificado del servidor
            byte[] bufferEntrada = new byte[1024];
            DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            socketCliente.receive(paqueteEntrada);

            // Convertir bytes a objeto Persona
            ByteArrayInputStream bais = new ByteArrayInputStream(paqueteEntrada.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Persona personaModificada = (Persona) ois.readObject();

            System.out.println("Objeto recibido del servidor: " + personaModificada);

            socketCliente.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
