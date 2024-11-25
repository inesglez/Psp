package com.example.tema3.actividad3_1;

import java.net.*;
import java.util.Scanner;

import java.net.*;
import java.util.Scanner;

public class actividad3_1 {
    public static void main(String[] args) {
        // Crear un objeto Scanner para leer desde la consola
        Scanner scanner = new Scanner(System.in);

        // Pedir al usuario que ingrese un nombre de máquina o una dirección IP
        System.out.println("Por favor ingrese un nombre de máquina o una dirección IP:");

        // Leer la entrada del usuario
        String input = scanner.nextLine();  // lee toda la línea

        try {
            // Obtener la dirección IP o el nombre de host
            InetAddress address = InetAddress.getByName(input);

            // Mostrar la información
            System.out.println("Información sobre: " + input);
            System.out.println("Dirección IP: " + address.getHostAddress());
            System.out.println("Nombre de la máquina (hostname): " + address.getHostName());

        } catch (UnknownHostException e) {
            // Si no se puede resolver el nombre o la IP, mostrar el error
            System.out.println("No se pudo encontrar información para: " + input);
            System.out.println("Detalles del error: " + e.getMessage());
        } finally {
            scanner.close();  // Cerrar el scanner para liberar recursos
        }
    }
}
