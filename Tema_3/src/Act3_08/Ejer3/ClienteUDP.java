package Act3_08.Ejer3;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socketCliente = new DatagramSocket();
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            int puertoServidor = 9876;

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Introducir idalumno
                System.out.print("Introduce el ID del alumno (* para salir): ");
                String idalumno = scanner.nextLine();

                if (idalumno.equals("*")) {
                    System.out.println("Saliendo del programa...");
                    break;
                }

                // Enviar ID al servidor
                byte[] bufferSalida = idalumno.getBytes();
                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionServidor, puertoServidor);
                socketCliente.send(paqueteSalida);

                // Recibir objeto Alumno del servidor
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socketCliente.receive(paqueteEntrada);

                ByteArrayInputStream bais = new ByteArrayInputStream(paqueteEntrada.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Alumno alumnoRecibido = (Alumno) ois.readObject();

                System.out.println("Datos del alumno: " + alumnoRecibido);
            }
            socketCliente.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
