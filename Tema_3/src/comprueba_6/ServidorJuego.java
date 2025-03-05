package comprueba_6;
import java.io.*;
import java.net.*;
import java.util.*;

class ServidorJuego {
    private static final int PUERTO = 5000;
    private static final String[][] TABLERO = new String[3][4];
    private static final List<int[]> PREMIOS = Arrays.asList(
            new int[]{0, 0}, new int[]{1, 2}, new int[]{2, 0}, new int[]{2, 3}
    );
    private static int clienteID = 1;

    public static void main(String[] args) {
        inicializarTablero();
        System.out.println("Servidor iniciado...");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new ClienteHandler(socket, clienteID++).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void inicializarTablero() {
        for (int[] premio : PREMIOS) {
            TABLERO[premio[0]][premio[1]] = "Premio";
        }
    }

    static class ClienteHandler extends Thread {
        private Socket socket;
        private int idCliente;

        ClienteHandler(Socket socket, int idCliente) {
            this.socket = socket;
            this.idCliente = idCliente;
        }

        @Override
        public void run() {
            try (
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream())
            ) {
                out.writeInt(idCliente);
                int intentos = 0;
                int premiosGanados = 0;

                while (intentos < 4) {
                    int fila = in.readInt();
                    int columna = in.readInt();

                    if (fila < 0 || fila >= 3 || columna < 0 || columna >= 4) {
                        out.writeUTF("Posición fuera del rango. Intente de nuevo.");
                        continue;
                    }

                    if (TABLERO[fila][columna] != null) {
                        premiosGanados++;
                        out.writeUTF("¡Ganaste un premio!");
                        TABLERO[fila][columna] = null;
                    } else {
                        out.writeUTF("Sin premio en esta posición.");
                    }

                    intentos++;
                    out.writeUTF("Intentos: " + intentos + " - Premios: " + premiosGanados);
                }

                out.writeUTF("Juego terminado para el Cliente " + idCliente);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}