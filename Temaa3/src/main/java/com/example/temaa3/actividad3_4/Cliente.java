package com.example.temaa3.actividad3_4;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String servidorIP = "127.0.0.1";  // IP del servidor (localhost para este ejemplo)
        int puerto = 12345;  // El puerto en el que el servidor escucha

        try (Socket socket = new Socket(servidorIP, puerto)) {
            // Crear los flujos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar al usuario que ingrese un número entero
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce un número entero: ");
            int numero = scanner.nextInt();

            // Enviar el número al servidor
            salida.println(numero);
            System.out.println("Número enviado al servidor: " + numero);

            // Leer la respuesta (el cuadrado) del servidor
            String respuesta = entrada.readLine();
            int cuadrado = Integer.parseInt(respuesta);

            // Mostrar el resultado
            System.out.println("El cuadrado de " + numero + " es: " + cuadrado);

        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
