package comprueba_5;

import java.io.*;
import java.net.*;

class ServidorMayusculas {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(44444);
        System.out.println("Servidor iniciado...");

        while (true) {
            Socket socket = serverSocket.accept();
            new HiloServidor(socket).start();
        }
    }
}