package comprueba_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ClienteJuego {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            int idCliente = in.readInt();
            System.out.println("Cliente ID: " + idCliente);

            while (true) {
                System.out.print("Introduce fila (0-2): ");
                int fila = scanner.nextInt();
                System.out.print("Introduce columna (0-3): ");
                int columna = scanner.nextInt();

                out.writeInt(fila);
                out.writeInt(columna);

                System.out.println(in.readUTF());
                System.out.println(in.readUTF());
            }
        } catch (IOException e) {
            System.out.println("Juego terminado.");
        }
    }
}

