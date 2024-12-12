package com.example.tema3.actividad3_5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 6000; // Puerto donde el servidor escuchará
        int n = 3; // Número de clientes que el servidor manejará

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado, esperando hasta " + n + " clientes...");

            // Aceptar hasta n clientes
            for (int i = 1; i <= n; i++) {
                Socket clienteConectado = servidor.accept(); // Espera la conexión del cliente
                System.out.println("Cliente " + i + " conectado");

                // Crear una instancia de la clase ClienteHandler y pasar el cliente y su número
                ContadorClientes contClientes = new ContadorClientes(clienteConectado, i);
                contClientes.start(); // Inicia el hilo
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}