package Act3_05.Ejer_01;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        String host = "localhost"; // Dirección del servidor
        int puerto = 6000; // Puerto donde el servidor está escuchando

        Socket socket = new Socket(host, puerto);
        DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream());
        DataInputStream flujoEntrada = new DataInputStream(socket.getInputStream());
        Scanner scanner = new Scanner(System.in);

        System.out.println("Conectado al servidor. Escribe cadenas para enviar (escribe '*' para terminar).");

        // Bucle para leer cadenas y enviarlas
        while (true) {
            System.out.print("Introduce una cadena: ");
            String cadena = scanner.nextLine();

            // Enviar la cadena al servidor
            flujoSalida.writeUTF(cadena);

            // Si la cadena es un asterisco, se termina
            if (cadena.equals("*")) {
                break;
            }

            // Recibir la respuesta del servidor
            int numeroCaracteres = flujoEntrada.readInt();
            System.out.println("El servidor ha recibido la cadena y tiene " + numeroCaracteres + " caracteres.");
        }

    }
}

