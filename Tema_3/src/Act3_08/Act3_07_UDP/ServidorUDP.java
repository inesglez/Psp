package Act3_08.Act3_07_UDP;

import java.io.*;
import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) {
        int puerto = 6000;

        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor UDP esperando datos...");

            while (true) {
                // Recibir datagrama del cliente
                byte[] bufferRecepcion = new byte[1024];
                DatagramPacket paqueteRecepcion = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);
                socket.receive(paqueteRecepcion);

                // Deserializar objeto Numeros
                ByteArrayInputStream bais = new ByteArrayInputStream(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Numeros numeros = (Numeros) ois.readObject();

                // Si el número es menor o igual a 0, salir
                if (numeros.getNumero() <= 0) {
                    System.out.println("Número recibido menor o igual a 0. Finalizando servidor.");
                    break;
                }

                // Calcular cuadrado y cubo
                int numero = numeros.getNumero();
                numeros.setCuadrado((long) numero * numero);
                numeros.setCubo((long) numero * numero * numero);

                // Serializar objeto Numeros actualizado
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(numeros);
                byte[] bufferEnvio = baos.toByteArray();

                // Enviar datagrama de respuesta al cliente
                InetAddress direccionCliente = paqueteRecepcion.getAddress();
                int puertoCliente = paqueteRecepcion.getPort();
                DatagramPacket paqueteEnvio = new DatagramPacket(bufferEnvio, bufferEnvio.length, direccionCliente, puertoCliente);
                socket.send(paqueteEnvio);

                System.out.println("Procesado: Número = " + numeros.getNumero() + ", Cuadrado = " + numeros.getCuadrado() + ", Cubo = " + numeros.getCubo());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
        System.out.println("Servidor finalizado.");
    }
}
