package Act3_08.Act3_07_UDP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String host = "localhost"; // Dirección del servidor
        int puerto = 6000;         // Puerto del servidor

        try (DatagramSocket socket = new DatagramSocket()) {
            while (true) {
                System.out.print("Introduce un número mayor a 0 (o <= 0 para salir): ");
                int numero = 0;

                // Validar entrada
                try {
                    numero = scanner.nextInt();
                } catch (Exception e) {
                    System.err.println("Error: Debes introducir un número entero.");
                    scanner.nextLine(); // Limpiar buffer
                    continue;
                }

                // Si el número es menor o igual a 0, salir
                if (numero <= 0) {
                    System.out.println("Número menor o igual a 0. Cliente finalizando.");
                    break;
                }

                // Crear objeto Numeros y serializar
                Numeros numeros = new Numeros(numero);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(numeros);
                byte[] bufferEnvio = baos.toByteArray();

                // Enviar datagrama al servidor
                DatagramPacket paqueteEnvio = new DatagramPacket(bufferEnvio, bufferEnvio.length, InetAddress.getByName(host), puerto);
                socket.send(paqueteEnvio);

                // Recibir respuesta del servidor
                byte[] bufferRecepcion = new byte[1024];
                DatagramPacket paqueteRecepcion = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);
                socket.receive(paqueteRecepcion);

                // Deserializar objeto recibido
                ByteArrayInputStream bais = new ByteArrayInputStream(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
                ObjectInputStream ois = new ObjectInputStream(bais);
                numeros = (Numeros) ois.readObject();

                // Mostrar resultados
                System.out.println("Número: " + numeros.getNumero());
                System.out.println("Cuadrado: " + numeros.getCuadrado());
                System.out.println("Cubo: " + numeros.getCubo());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en la comunicación con el servidor: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
