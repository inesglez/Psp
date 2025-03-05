package Act3_07;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String host = "localhost";  // Dirección del servidor
        int puerto = 6000;  // Puerto del servidor

        while (true) {
            int numero = 0;

            // Solicitar un número con validación
            try {
                System.out.print("Introduce un número mayor a 0 (o <= 0 para salir): ");
                numero = scanner.nextInt();
            } catch (Exception e) {
                System.err.println("Error: Debes introducir un número entero.");
                scanner.nextLine(); // Limpiar el buffer
                continue;
            }

            // Verificar si el número es menor o igual a 0
            if (numero <= 0) {
                System.out.println("Número menor o igual a 0. Cliente finalizando.");
                break;
            }

            try (Socket socket = new Socket(host, puerto)) {
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

                // Crear y enviar el objeto Numeros
                Numeros numeros = new Numeros(numero);
                salida.writeObject(numeros);

                // Recibir objeto con resultados
                numeros = (Numeros) entrada.readObject();
                System.out.println("Número: " + numeros.getNumero());
                System.out.println("Cuadrado: " + numeros.getCuadrado());
                System.out.println("Cubo: " + numeros.getCubo());

            } catch (ConnectException e) {
                System.err.println("Error: No se puede conectar al servidor. Asegúrate de que esté en ejecución.");
                break;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error en la comunicación con el servidor: " + e.getMessage());
                break;
            }
        }
        scanner.close();
    }
}
