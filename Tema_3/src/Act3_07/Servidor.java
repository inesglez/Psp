package Act3_07;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 6000;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor esperando al cliente...");

            // Aceptar la conexión de un cliente
            try (Socket clienteSocket = servidor.accept()) {
                System.out.println("Cliente conectado. Esperando datos...");

                ObjectInputStream entrada = new ObjectInputStream(clienteSocket.getInputStream());
                ObjectOutputStream salida = new ObjectOutputStream(clienteSocket.getOutputStream());

                while (true) {
                    try {
                        // Recibir objeto Numeros
                        Numeros numeros = (Numeros) entrada.readObject();

                        // Si el número es menor o igual a 0, terminar
                        if (numeros.getNumero() <= 0) {
                            System.out.println("Número recibido menor o igual a 0. Finalizando servidor.");
                            break;
                        }

                        // Calcular cuadrado y cubo
                        int numero = numeros.getNumero();
                        numeros.setCuadrado((long) numero * numero);
                        numeros.setCubo((long) numero * numero * numero);

                        // Enviar objeto con los resultados
                        salida.writeObject(numeros);
                        System.out.println("Calculados: Cuadrado = " + numeros.getCuadrado() + ", Cubo = " + numeros.getCubo());
                    } catch (EOFException e) {
                        // El cliente finalizó la conexión
                        System.out.println("El cliente ha finalizado la conexión. Cerrando servidor.");
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
        System.out.println("Servidor finalizado.");
    }
}
