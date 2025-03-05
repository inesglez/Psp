package actividad4;

//ACTIVIDAD 6 DEL TEMA 3

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.net.*;

public class Cliente {

    public static void main(String[] args) {
        String host = "localhost";  // Dirección del servidor
        int puerto = 6000;  // Puerto del servidor

        // Inicializar SSLSocket
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = null;

        try {
            socket = (SSLSocket) socketFactory.createSocket(host, puerto);

            // protocolos de seguridad que queremos permitir
            socket.setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.3"});

            BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader respuestaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                System.out.print("Introduce un mensaje (asterisco para salir): ");
                String mensaje = entradaTeclado.readLine();

                // Si el mensaje es un asterisco, termina
                if (mensaje.equals("*")) {
                    break;
                }
                // Enviar el mensaje al servidor
                salida.println(mensaje);

                // Recibir la respuesta del servidor
                String respuesta = respuestaServidor.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
