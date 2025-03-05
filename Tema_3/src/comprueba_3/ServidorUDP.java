package comprueba_3;

import java.net.*;
import java.io.*;

public class ServidorUDP {
    public static void main(String[] args) throws Exception {
        // Definir el puerto y el socket UDP
        DatagramSocket socket = new DatagramSocket(9876);

        // Crear un array de 5 alumnos
        Curso curso1 = new Curso("C001", "Matemáticas");
        Curso curso2 = new Curso("C002", "Física");
        Curso curso3 = new Curso("C003", "Química");
        Curso curso4 = new Curso("C004", "Historia");
        Curso curso5 = new Curso("C005", "Literatura");

        Alumno[] alumnos = new Alumno[5];
        alumnos[0] = new Alumno("A001", "Juan", curso1, 9);
        alumnos[1] = new Alumno("A002", "Maria", curso2, 7);
        alumnos[2] = new Alumno("A003", "Pedro", curso3, 8);
        alumnos[3] = new Alumno("A004", "Laura", curso4, 6);
        alumnos[4] = new Alumno("A005", "Ana", curso5, 10);

        System.out.println("Servidor esperando consultas...");

        while (true) {
            // Recibir los datos del cliente
            byte[] recibirDatos = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(recibirDatos, recibirDatos.length);
            socket.receive(paqueteRecibido);

            String idalumnoSolicitado = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
            System.out.println("Solicitado: " + idalumnoSolicitado);

            // Buscar el alumno por id
            Alumno alumnoEncontrado = null;
            for (Alumno a : alumnos) {
                if (a.getIdalumno().equals(idalumnoSolicitado)) {
                    alumnoEncontrado = a;
                    break;
                }
            }

            // Si no se encuentra el alumno, crear uno con datos indicando que no existe
            if (alumnoEncontrado == null) {
                alumnoEncontrado = new Alumno("No encontrado", "No existe", new Curso("N/A", "N/A"), 0);
            }

            // Enviar el objeto Alumno como respuesta al cliente
            InetAddress clienteDireccion = paqueteRecibido.getAddress();
            int puertoCliente = paqueteRecibido.getPort();

            // Convertir el objeto Alumno a String (serialización simple)
            String respuesta = alumnoEncontrado.toString();
            byte[] enviarDatos = respuesta.getBytes();

            DatagramPacket paqueteRespuesta = new DatagramPacket(enviarDatos, enviarDatos.length, clienteDireccion, puertoCliente);
            socket.send(paqueteRespuesta);
        }
    }
}

