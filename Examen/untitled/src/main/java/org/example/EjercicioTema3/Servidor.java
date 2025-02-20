package org.example.EjercicioTema3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;


public class Servidor {
    private static final int PUERTO = 6000;
    private static Map<Integer, Profesor> profesores = new HashMap<>();

    public static void main(String[] args) {
        inicializarProfesores();
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);
            while (true) {
                Socket cliente = servidor.accept();
                new HiloCliente(cliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void inicializarProfesores() {
        profesores.put(1, new Profesor(1, "Juan Perez", new String[]{"Matemáticas", "Física"}, new Especialidad(1, "Ciencias")));
        profesores.put(2, new Profesor(2, "Maria Lopez", new String[]{"Literatura", "Filosofía"}, new Especialidad(2, "Humanidades")));
        profesores.put(3, new Profesor(3, "Carlos Ramírez", new String[]{"Programación", "Bases de Datos"}, new Especialidad(3, "Informática")));
    }
