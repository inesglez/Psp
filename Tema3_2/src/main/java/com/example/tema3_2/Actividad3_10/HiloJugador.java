package com.example.tema3_2.Actividad3_10;


import java.io.*;
import java.net.*;
import java.util.Random;

class HiloJugador extends Thread {
    private Socket socket;

    public HiloJugador(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        ) {
            Random random = new Random();
            int numeroSecreto = random.nextInt(25) + 1; // Número entre 1 y 25
            System.out.println("Número secreto generado: " + numeroSecreto);

            int intentos = 0;
            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {
                intentos++;
                int numeroRecibido = Integer.parseInt(mensaje);
                System.out.println("Cliente adivinó: " + numeroRecibido);

                if (numeroRecibido > numeroSecreto) {
                    salida.println("Número demasiado grande");
                } else if (numeroRecibido < numeroSecreto) {
                    salida.println("Número demasiado pequeño");
                } else {
                    salida.println("¡Correcto! Adivinaste en " + intentos + " intentos.");
                    break;
                }
            }

            System.out.println("Cliente desconectado.");
            socket.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}