package comprueba_7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ClienteMastermind {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6000);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            int idJugador = in.readInt();
            System.out.println("Jugador ID: " + idJugador);

            while (true) {
                System.out.print("Introduce 4 números (0-9 sin repetir): ");
                int[] intento = {scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()};

                for (int num : intento) out.writeInt(num);

                System.out.println(in.readUTF());
                String mensaje = in.readUTF();
                System.out.println(mensaje);
                if (mensaje.contains("ganado") || mensaje.contains("terminado")) break;
            }
        } catch (IOException e) {
            System.out.println("Juego terminado.");
        }
    }
}
