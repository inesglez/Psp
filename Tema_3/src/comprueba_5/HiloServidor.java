package comprueba_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloServidor extends Thread {
    private Socket socket;

    public HiloServidor(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            String mensaje;

            while (!(mensaje = entrada.readLine()).equals("*")) {
                salida.println(mensaje.toUpperCase());
            }

            socket.close();
            System.out.println("Cliente desconectado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}