package Act3_05.Ejer_01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 6000; // Puerto donde el servidor escuchará

        try {
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado, esperando conexiones...");

            // Aceptar la conexión del cliente
            try {
                Socket cliente = servidor.accept();
                DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
                DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());

                System.out.println("Cliente conectado.");

                // Bucle para recibir las cadenas del cliente
                while (true) {
                    // Leer la cadena enviada por el cliente
                    String cadena = flujoEntrada.readUTF();

                    // Si la cadena es un asterisco, terminar
                    if (cadena.equals("*")) {
                        break;
                    }

                    // Enviar de vuelta el número de caracteres de la cadena
                    flujoSalida.writeInt(cadena.length());
                    System.out.println("La cadena introducida tiene: " + cadena.length() + " carácteres.");
                }

                System.out.println("Servidor finalizado.");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
