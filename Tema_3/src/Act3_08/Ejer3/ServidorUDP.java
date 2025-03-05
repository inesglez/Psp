package Act3_08.Ejer3;

import java.io.*;
import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) {
        int puerto = 6000;

        try {
            DatagramSocket socketServidor = new DatagramSocket(puerto);
            System.out.println("Servidor UDP en el puerto 9876...");

            // Inicializar datos de alumnos
            Curso curso1 = new Curso("201", "Programación");
            Curso curso2 = new Curso("202", "Base de Datos");

            Alumno[] alumnos = {
                    new Alumno("101", "Ángela Pedrera", curso1, 5),
                    new Alumno("102", "María González", curso2, 10),
                    new Alumno("103", "Cristina Gutiérrez", curso1, 7),
                    new Alumno("104", "Pedro Domínguez", curso2, 4),
                    new Alumno("105", "Mar Gómez", curso1, 3)
            };

            while (true) {
                // Recibir idAlumno del cliente
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socketServidor.receive(paqueteEntrada);

                String idalumnoRecibido = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
                System.out.println("ID recibido del cliente: " + idalumnoRecibido);

                // Buscar alumno
                Alumno alumnoEncontrado = null;
                for (Alumno alumno : alumnos) {
                    if (alumno.getIdalumno().equals(idalumnoRecibido)) {
                        alumnoEncontrado = alumno;
                        break;
                    }
                }

                // Si no se encuentra, devolver un objeto vacío con mensaje
                if (alumnoEncontrado == null) {
                    alumnoEncontrado = new Alumno(idalumnoRecibido, "Alumno no encontrado", new Curso("N/A", "N/A"), 0);
                }

                // Enviar objeto Alumno al cliente
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(alumnoEncontrado);

                byte[] bufferSalida = baos.toByteArray();
                InetAddress direccionCliente = paqueteEntrada.getAddress();
                int puertoCliente = paqueteEntrada.getPort();

                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionCliente, puertoCliente);
                socketServidor.send(paqueteSalida);

                System.out.println("Datos enviados al cliente: " + alumnoEncontrado);
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
