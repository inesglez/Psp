package comprueba_1;

import java.io.*;
import java.net.*;

public class ClienteTCP {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String mensaje;
        System.out.println("Introduce texto (* para salir):");
        while (!(mensaje = teclado.readLine()).equals("*")) {
            salida.println(mensaje);
            System.out.println("Número de caracteres: " + entrada.readLine());
        }
        salida.println("*");
        socket.close();
    }
}