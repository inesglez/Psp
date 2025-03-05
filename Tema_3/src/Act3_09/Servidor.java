package Act3_09;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 44444;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado, esperando clientes...");

            while (true) {
                Socket socket = servidor.accept(); // Acepta una nueva conexión
                System.out.println(">> Conecta IP " + socket.getInetAddress() + ", Puerto remoto: " + socket.getPort());

                // Crear un nuevo hilo para atender al cliente
                HiloCliente hilo = new HiloCliente(socket);
                hilo.start(); // Inicia el hilo
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
