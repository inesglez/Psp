package org.example.EjercicioTema3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

private static class HiloCliente extends Thread {
    private Socket socket;

    public HiloCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Introduce el ID del profesor a consultar:");
            int idProfesor = Integer.parseInt(in.readLine());
            Profesor profesor = profesores.get(idProfesor);
            if (profesor != null) {
                out.println(profesor.toString());
            } else {
                out.println("Profesor no encontrado");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}
