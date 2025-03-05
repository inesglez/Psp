package comprueba_1;

import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor iniciado...");

        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado...");
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

        String mensaje;
        while (!(mensaje = entrada.readLine()).equals("*")) {
            System.out.println("Mensaje recibido: " + mensaje);
            salida.println(mensaje.length());
        }
        socket.close();
        serverSocket.close();
    }
}