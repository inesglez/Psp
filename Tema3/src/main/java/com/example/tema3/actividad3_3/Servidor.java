package com.example.tema3.actividad3_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 6000; // Puerto donde el servidor escuchará

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Esperando al cliente...");
            Socket clienteConectado = servidor.accept(); // Espera la conexión del cliente

            // Creación del flujo de entrada del Cliente
            InputStream entrada = clienteConectado.getInputStream();
            DataInputStream flujoEntrada = new DataInputStream(entrada);

            // El Cliente me envía su mensaje
            String mensajeDelCliente = flujoEntrada.readUTF();
            System.out.println("Mensaje recibido del cliente: \n\t" + mensajeDelCliente);

            // Creación del flujo de salida al Cliente
            OutputStream salida = clienteConectado.getOutputStream();
            DataOutputStream flujoSalida = new DataOutputStream(salida);

            // Envío un mensaje al Cliente
            String saludo = "¡Hola, Cliente!";
            flujoSalida.writeUTF(saludo);
            System.out.println("Enviando al cliente: \n\t" + saludo);

            // Recibir respuesta del cliente (mensaje en minúsculas)
            String respuestaCliente = flujoEntrada.readUTF();
            System.out.println("Respuesta en minúsculas del cliente: \n\t" + respuestaCliente);

            // Cerrar Streams y Socket
            flujoEntrada.close();
            flujoSalida.close();
            entrada.close();
            salida.close();
            clienteConectado.close();
            servidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}