package Act3_11;

import java.io.*;
import java.net.*;

public class ServidorChat {
    static final int max = 10; // máximo de conexiones permitidas

    public static void main(String[] args) throws IOException {
        int puerto = 44444;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado...");

        Socket tabla[] = new Socket[max]; // control clientes
        ComunHilos comun = new ComunHilos(max, 0, 0, tabla);

        while (true) {
            Socket socket = new Socket();
            socket = servidor.accept();

            // Objeto compartido por los hilos
            comun.addTabla(socket, comun.getConexiones());
            comun.setActuales(comun.getConexiones() + 1);
            comun.setConexiones(comun.getConexiones() + 1);

            HiloServidorChat hilo = new HiloServidorChat(socket, comun);
            hilo.start();
        }
//        servidor.close();
    }
}