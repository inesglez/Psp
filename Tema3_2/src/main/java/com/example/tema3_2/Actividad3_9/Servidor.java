package com.example.tema3_2.Actividad3_9;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(44444)) {  // Crea un ServerSocket en el puerto 44444
            System.out.println("Servidor iniciado...");
            while (true) {
                // Acepta una nueva conexión de cliente
                Socket clienteSocket = serverSocket.accept();
                InetAddress clienteAddress = clienteSocket.getInetAddress();  // Obtiene la dirección IP del cliente
                System.out.println("Conectado IP " + clienteAddress + ", Puerto remoto: " + clienteSocket.getPort());  // Muestra la IP y el puerto del cliente
                // Crea un hilo para atender a cada cliente
                new Thread(new AtenderCliente(clienteSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
