package comprueba_4;
import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    private static int clientId = 1; // Identificador para los clientes
    private static Profesor[] profesores = new Profesor[5];

    public static void main(String[] args) {
        try {
            // Inicializando datos
            inicializarDatos();

            ServerSocket serverSocket = new ServerSocket(6000);
            System.out.println("Servidor iniciado...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente " + clientId + " conectado");

                // Enviar identificador al cliente
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                out.writeInt(clientId);

                // Crear un nuevo hilo para manejar la comunicación con el cliente
                new ClienteHandler(clientSocket, clientId).start();

                clientId++; // Incrementar el identificador para el próximo cliente
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void inicializarDatos() {
        Asignatura[] asignaturas1 = {
                new Asignatura(2, "ADAT"),
                new Asignatura(3, "PSP"),
                new Asignatura(4, "PMD")
        };
        Especialidad especialidad1 = new Especialidad(1, "INFORMÁTICA");
        profesores[0] = new Profesor(1, "María Jesús", asignaturas1, especialidad1);

        Asignatura[] asignaturas2 = {
                new Asignatura(5, "LENGUAJE DE PROGRAMACIÓN"),
                new Asignatura(6, "FUNDAMENTOS DE BASES DE DATOS")
        };
        Especialidad especialidad2 = new Especialidad(2, "MATEMÁTICAS");
        profesores[1] = new Profesor(2, "José Luis", asignaturas2, especialidad2);

        // Agregar más profesores de manera similar...
    }

    // Clase interna para manejar a cada cliente
    private static class ClienteHandler extends Thread {
        private Socket clientSocket;
        private int clientId;

        public ClienteHandler(Socket clientSocket, int clientId) {
            this.clientSocket = clientSocket;
            this.clientId = clientId;
        }

        @Override
        public void run() {
            try {
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                // Mostrar el identificador solicitado
                out.writeUTF("SOY EL CLIENTE: " + clientId);
                out.writeUTF("Introduce identificador a consultar: ");
                while (true) {
                    String idProfesor = in.readUTF();
                    if (idProfesor.equals("*")) {
                        break;
                    }

                    int id = Integer.parseInt(idProfesor);
                    Profesor profesor = obtenerProfesor(id);
                    if (profesor != null) {
                        out.writeUTF("Nombre: " + profesor.getNombre());
                        out.writeUTF("Especialidad: " + profesor.getEspe().getId() + " - " + profesor.getEspe().getNombreespe());
                        for (Asignatura asignatura : profesor.getAsignaturas()) {
                            out.writeUTF("Asignatura: " + asignatura.getId() + " - " + asignatura.getNombreasig());
                        }
                    } else {
                        out.writeUTF("El profesor con id " + id + " no existe.");
                    }
                }
                System.out.println("EL CLIENTE " + clientId + " HA FINALIZADO");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Profesor obtenerProfesor(int id) {
            for (Profesor p : profesores) {
                if (p != null && p.getIdprofesor() == id) {
                    return p;
                }
            }
            return null;
        }
    }
}
