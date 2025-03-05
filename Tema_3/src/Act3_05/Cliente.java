package Act3_05;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost"; // Dirección del servidor
        int puerto = 6000; // Puerto del servidor remoto

        System.out.println("Programa cliente iniciado...");

        try (Socket cliente = new Socket(host, puerto)) {
            // Crear flujo de entrada para recibir mensaje del servidor
            DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());

            // Recibir el mensaje del servidor
            String mensaje = flujoEntrada.readUTF();
            System.out.println("Mensaje recibido del servidor: " + mensaje);

            // Cerrar el flujo y el socket
            flujoEntrada.close();
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
