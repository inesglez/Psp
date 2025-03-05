package actividad4;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class Servidor {

    public static void main(String[] args) {
        int puerto = 6000;
        // Inicializar SSLServerSocket
        SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serverSocket = null;

        try {
            serverSocket = (SSLServerSocket) socketFactory.createServerSocket(puerto);
            System.out.println("Servidor SSL iniciado en el puerto " + puerto);

            // protocolos de seguridad que queremos permitir (opcional)
            serverSocket.setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.3"});

            // Bucle para aceptar conexiones de clientes
            while (true) {
                SSLSocket socketCliente = (SSLSocket) serverSocket.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress());

                BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter salidaCliente = new PrintWriter(socketCliente.getOutputStream(), true);

                String mensaje;
                while ((mensaje = entradaCliente.readLine()) != null) {
                    System.out.println("Mensaje recibido del cliente: " + mensaje);

                    // Si el mensaje es un asterisco, terminar el servidor
                    if (mensaje.equals("*")) {
                        System.out.println("Servidor finalizado.");
                        break;
                    }

                    // Convertir el mensaje a mayúsculas
                    String mensajeMayusculas = mensaje.toUpperCase();

                    // Enviar el mensaje en mayúsculas de vuelta al cliente
                    salidaCliente.println(mensajeMayusculas);
                }

                socketCliente.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
