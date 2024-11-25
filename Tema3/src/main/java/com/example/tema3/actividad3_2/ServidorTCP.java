package com.example.tema3.actividad3_2;

import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) {
        // Paso 1: Definir el puerto en el que el servidor escuchará las conexiones
        int puerto = 12345;  // El servidor escuchará en el puerto 12345

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            // Paso 2: Crear un ServerSocket que escucha en el puerto 12345
            System.out.println("Servidor en espera de clientes en el puerto " + puerto);

            // Paso 3: Aceptar hasta 2 clientes (podemos hacer esto usando un bucle)
            for (int i = 1; i <= 2; i++) {
                // Paso 4: Esperamos que un cliente se conecte al servidor
                Socket socketCliente = serverSocket.accept();
                System.out.println("Cliente " + i + " conectado.");

                // Paso 5: Obtener información sobre la conexión del cliente
                InetAddress clienteIP = socketCliente.getInetAddress();  // IP del cliente
                int puertoRemoto = socketCliente.getPort();  // Puerto remoto del cliente
                int puertoLocal = socketCliente.getLocalPort();  // Puerto local del servidor

                // Paso 6: Mostrar la información del cliente conectado
                System.out.println("Cliente " + i + ":");
                System.out.println("Dirección IP remota: " + clienteIP.getHostAddress());
                System.out.println("Puerto remoto: " + puertoRemoto);
                System.out.println("Puerto local: " + puertoLocal);

                // Paso 7: Cerrar el socket del cliente después de mostrar la información
                socketCliente.close();
            }
        } catch (IOException e) {
            // Paso 8: Manejo de errores
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
