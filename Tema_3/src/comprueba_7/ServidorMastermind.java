package comprueba_7;

import java.io.*;
import java.net.*;
import java.util.*;

class ServidorMastermind {
    private static final int PUERTO = 6000;
    private static final int[] COMBINACION = generarCombinacion();
    private static boolean juegoTerminado = false;
    private static int jugadorID = 1;

    public static void main(String[] args) {
        System.out.println("Servidor iniciado...");
        System.out.println("Combinación secreta: " + Arrays.toString(COMBINACION));

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (!juegoTerminado) {
                Socket socket = serverSocket.accept();
                new JugadorHandler(socket, jugadorID++).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generarCombinacion() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 0; i < 10; i++) numeros.add(i);
        Collections.shuffle(numeros);
        return new int[]{numeros.get(0), numeros.get(1), numeros.get(2), numeros.get(3)};
    }

    static class JugadorHandler extends Thread {
        private Socket socket;
        private int idJugador;

        JugadorHandler(Socket socket, int idJugador) {
            this.socket = socket;
            this.idJugador = idJugador;
        }

        @Override
        public void run() {
            try (
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream())
            ) {
                out.writeInt(idJugador);
                int intentos = 0;

                while (intentos < 10 && !juegoTerminado) {
                    int[] intento = {in.readInt(), in.readInt(), in.readInt(), in.readInt()};
                    int muertos = 0, heridos = 0;

                    for (int i = 0; i < 4; i++) {
                        if (intento[i] == COMBINACION[i]) {
                            muertos++;
                        } else if (Arrays.stream(COMBINACION).anyMatch(n -> n == intento[i])) {
                            heridos++;
                        }
                    }

                    out.writeUTF("Muertos: " + muertos + ", Heridos: " + heridos);
                    intentos++;

                    if (muertos == 4) {
                        juegoTerminado = true;
                        out.writeUTF("¡Felicidades! Has ganado.");
                    }
                }

                out.writeUTF("Juego terminado para el Jugador " + idJugador);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
