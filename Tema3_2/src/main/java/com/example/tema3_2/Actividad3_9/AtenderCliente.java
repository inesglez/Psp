package com.example.tema3_2.Actividad3_9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

class AtenderCliente implements Runnable {
    private Socket clienteSocket;  // Socket para la conexión con el cliente

    public AtenderCliente(Socket clienteSocket) {
        this.clienteSocket = clienteSocket;  // Inicializa el socket del cliente
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));  // Lee los datos enviados por el cliente
             PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true)) {  // Envia respuestas al cliente

            String mensaje;
            while ((mensaje = in.readLine()) != null) {  // Lee los mensajes enviados por el cliente
                if (mensaje.equals("*")) {  // Si el mensaje es "*", termina la conexión
                    break;
                }
                out.println(mensaje.toUpperCase());  // Envía el mensaje al cliente convertido a mayúsculas
            }

            InetAddress clienteAddress = clienteSocket.getInetAddress();  // Obtiene la dirección IP del cliente
            System.out.println("Desconectado IP " + clienteAddress + ", Puerto remoto: " + clienteSocket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
