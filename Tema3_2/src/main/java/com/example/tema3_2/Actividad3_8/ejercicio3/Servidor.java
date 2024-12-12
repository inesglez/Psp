package com.example.tema3_2.Actividad3_8.ejercicio3;

import java.net.*;
import java.util.*;

public class Servidor {
    public static void main(String[] args) {
        // Crear un array con 5 objetos Alumno
        Alumno[] alumnos = new Alumno[] {
                new Alumno("1", "Juan Perez", "Matematicas", 8),
                new Alumno("2", "Maria Lopez", "Historia", 7),
                new Alumno("3", "Carlos Gomez", "Ciencias", 9),
                new Alumno("4", "Ana Martinez", "Literatura", 6),
                new Alumno("5", "Pedro Ruiz", "Física", 10)
        };

        try (DatagramSocket socket = new DatagramSocket(9876)) {
            byte[] receiveData = new byte[1024];
            System.out.println("Servidor UDP iniciado...");
            while (true) {
                // Recibir solicitud del cliente
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String idAlumno = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Buscar el Alumno por id
                Alumno alumno = null;
                for (Alumno a : alumnos) {
                    if (a.getIdAlumno().equals(idAlumno)) {
                        alumno = a;
                        break;
                    }
                }

                // Si no existe, enviamos todos los alumnos
                if (alumno == null) {
                    String allAlumnos = Arrays.toString(alumnos);
                    socket.send(new DatagramPacket(allAlumnos.getBytes(), allAlumnos.length(),
                            receivePacket.getAddress(), receivePacket.getPort()));
                } else {
                    // Si existe, enviamos el alumno encontrado
                    socket.send(new DatagramPacket(alumno.toString().getBytes(),
                            alumno.toString().length(), receivePacket.getAddress(), receivePacket.getPort()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
