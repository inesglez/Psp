package com.example.temaa3.actividad3_4;


import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 12345;  // Puerto en el que el servidor escucha

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor en espera de clientes en el puerto " + puerto);

            // Esperar a que el cliente se conecte
            try (Socket socketCliente = serverSocket.accept()) {
                System.out.println("Cliente conectado desde: " + socketCliente.getInetAddress().getHostAddress());

                // Crear los flujos de entrada y salida
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

                // Leer el número entero enviado por el cliente
                String mensaje = entrada.readLine();
                int numero = Integer.parseInt(mensaje);
                System.out.println("Número recibido del cliente: " + numero);

                // Calcular el cuadrado del número
                int cuadrado = numero * numero;

                // Enviar el resultado (cuadrado) al cliente
                salida.println(cuadrado);
                System.out.println("Enviando al cliente el cuadrado: " + cuadrado);
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
