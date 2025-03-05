package Act3_04;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 6000; // Puerto donde el servidor escuchará

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Esperando al cliente...");
            Socket clienteConectado = servidor.accept(); // Espera la conexión del cliente

            // Creación del flujo de entrada del Cliente
            InputStream entrada = clienteConectado.getInputStream();
            DataInputStream flujoEntrada = new DataInputStream(entrada);

            // El Cliente me envía el número
            double numeroCliente = flujoEntrada.readDouble();
            System.out.println("Número recibido del cliente: \n\t" + numeroCliente);

            // Calcular el cuadrado del número
            double cuadrado = Math.pow(numeroCliente, 2);

            // Creación del flujo de salida al Cliente
            OutputStream salida = clienteConectado.getOutputStream();
            DataOutputStream flujoSalida = new DataOutputStream(salida);

            // Enviar el cuadrado del número al Cliente
            flujoSalida.writeDouble(cuadrado);
            System.out.println("Enviando al cliente el cuadrado del número: \n\t" + cuadrado);

            // Cerrar Streams y Socket
            flujoEntrada.close();
            flujoSalida.close();
            entrada.close();
            salida.close();
            clienteConectado.close();
            servidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
