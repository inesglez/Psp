package com.example.tema3_2.Actividad3_8.ejercicio3;

import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {  // Crea un socket UDP
            Scanner scanner = new Scanner(System.in);
            InetAddress serverAddress = InetAddress.getByName("localhost");  // Dirección del servidor

            while (true) {
                System.out.print("Introduce el idAlumno (o * para salir): ");
                String idAlumno = scanner.nextLine();

                if (idAlumno.equals("*")) {  // Termina si el usuario introduce "*"
                    break;
                }

                // Envía el idAlumno al servidor
                DatagramPacket sendPacket = new DatagramPacket(idAlumno.getBytes(),
                        idAlumno.length(), serverAddress, 9876);
                socket.send(sendPacket);  // Envía el paquete

                // Recibe la respuesta del servidor
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String respuesta = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
