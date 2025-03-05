package comprueba_3;

import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Crear un socket UDP
        DatagramSocket socket = new DatagramSocket();

        // Dirección del servidor y puerto
        InetAddress servidorDireccion = InetAddress.getByName("localhost");
        int puertoServidor = 9876;

        while (true) {
            // Solicitar el idalumno al usuario
            System.out.print("Introduce el ID del alumno a consultar (o * para salir): ");
            String idalumno = scanner.nextLine();

            if (idalumno.equals("*")) {
                break; // Salir del bucle
            }

            // Enviar el idalumno al servidor
            byte[] enviarDatos = idalumno.getBytes();
            DatagramPacket paqueteEnvio = new DatagramPacket(enviarDatos, enviarDatos.length, servidorDireccion, puertoServidor);
            socket.send(paqueteEnvio);

            // Recibir la respuesta del servidor
            byte[] recibirDatos = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(recibirDatos, recibirDatos.length);
            socket.receive(paqueteRecibido);

            // Mostrar los datos del alumno recibido
            String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
            System.out.println("Respuesta del servidor: " + respuesta);
        }

        socket.close();
    }
}
