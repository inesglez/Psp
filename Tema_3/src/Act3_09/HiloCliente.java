package Act3_09;

import java.io.*;
import java.net.*;

class HiloCliente extends Thread {
    private Socket socket;

    public HiloCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                DataInputStream entrada = new DataInputStream(socket.getInputStream());
                DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
        ) {
            String mensaje;

            while (true) {
                mensaje = entrada.readUTF(); // Recibe la cadena

                if (mensaje.equals("*")) {
                    System.out.println(">> Desconecta IP " + socket.getInetAddress() + ", Puerto remoto: " + socket.getPort());
                    break;
                }

                // Envía el texto en mayúsculas
                String respuesta = mensaje.toUpperCase();
                salida.writeUTF(respuesta);
            }
        } catch (EOFException e) {
            System.out.println("\t>> Cliente desconectado inesperadamente.");
        } catch (IOException e) {
            System.out.println("Error en la comunicación: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el socket: " + e.getMessage());
            }
        }
    }
}
