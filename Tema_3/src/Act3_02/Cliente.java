package Act3_02;

import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost"; // Dirección del servidor
        int puerto = 6000; // Puerto del servidor

        try (Socket cliente = new Socket(host, puerto)) {
            System.out.println("Conectado al servidor:");
            System.out.println("\tPuerto local del cliente: " + cliente.getLocalPort());
            System.out.println("\tPuerto remoto del servidor: " + cliente.getPort());
            System.out.println("\tDirección IP del servidor: " + cliente.getInetAddress().getHostAddress());
            System.out.println("\tNombre del host del servidor: " + cliente.getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
