package Act3_04;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost"; // Dirección del servidor
        int puerto = 6000; // Puerto del servidor remoto

        System.out.println("Programa cliente iniciado...");

        try (Socket cliente = new Socket(host, puerto)) {
            // Crear un Scanner para leer la entrada del usuario
            Scanner scanner = new Scanner(System.in);

            // Solicitar al usuario que ingrese un número
            System.out.print("Introduce un número: ");
            double numero = scanner.nextDouble();

            // Crear flujo de salida para enviar el número al servidor
            DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());

            // Enviar el número al servidor
            flujoSalida.writeDouble(numero);
            System.out.println("Número enviado al servidor: " + numero);

            // Crear flujo de entrada para recibir la respuesta del servidor
            DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());

            // Recibir el cuadrado del número desde el servidor
            double cuadrado = flujoEntrada.readDouble();
            System.out.println("El cuadrado del número recibido del servidor: " + cuadrado);

            // Cerrar flujos y socket
            flujoSalida.close();
            flujoEntrada.close();
            scanner.close();
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
