package com.example.tema3.actividad3_2;

import java.io.*;
import java.net.*;

public class ClienteTCP {
    public static void main(String[] args) {
        // Paso 1: Definir la IP del servidor y el puerto al que se conectará
        String servidorIP = "127.0.0.1";  // Usamos localhost (127.0.0.1) para este ejemplo
        int puerto = 12345;  // El servidor escucha en el puerto 12345

        try (Socket socket = new Socket(servidorIP, puerto)) {
            // Paso 2: Conectar al servidor usando la IP y el puerto
            InetAddress servidorDireccion = socket.getInetAddress();  // Obtener la IP del servidor
            int puertoLocal = socket.getLocalPort();  // Obtener el puerto local del cliente
            int puertoRemoto = socket.getPort();  // Obtener el puerto remoto del servidor
            String ipRemota = servidorDireccion.getHostAddress();  // Obtener la IP remota del servidor

            // Paso 3: Mostrar la información de la conexión del cliente
            System.out.println("Conectado al servidor en: " + servidorIP);
            System.out.println("Puerto local: " + puertoLocal);
            System.out.println("Puerto remoto: " + puertoRemoto);
            System.out.println("Dirección IP remota: " + ipRemota);

        } catch (IOException e) {
            // Paso 4: Manejo de errores si no podemos conectarnos al servidor
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
