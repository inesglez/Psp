package Act3_02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 6000; // Puerto donde el servidor escuchará

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor TCP iniciado en el puerto " + puerto);

            // Aceptar dos conexiones de cliente
            for (int i = 1; i <= 2; i++) {
                System.out.println("Esperando conexión del cliente " + i + "...");
                Socket cliente = servidor.accept(); // Espera la conexión del cliente

                System.out.println("Cliente " + i + " conectado:");
                System.out.println("\tPuerto local del servidor: " + cliente.getLocalPort());
                System.out.println("\tPuerto remoto del cliente: " + cliente.getPort());
                System.out.println("\tDirección IP del cliente: " + cliente.getInetAddress().getHostAddress());
                System.out.println("\tNombre del host del cliente: " + cliente.getInetAddress().getHostName());

                cliente.close(); // Cerramos la conexión con el cliente
                System.out.println("Conexión con el cliente " + i + " cerrada.\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
