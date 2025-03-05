package Act3_11;

import java.io.*;
import java.net.*;

public class HiloServidorChat extends Thread {
    DataInputStream fentrada;
    Socket socket = null;
    ComunHilos comun;

    public HiloServidorChat(Socket s, ComunHilos comun) {
        this.socket = s;
        this.comun = comun;
        try {
            // Creo un flujo de entrada para leer los mensajes
            fentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Número de conexiones actuales: " + comun.getActuales());

        // Nada más conectarse se envían todos los mensajes
        String texto = comun.getMensajes();
        EnviarMensajeTodos(texto);

        while (true) {
            String cadena = "";
            try {
                cadena = fentrada.readUTF();
                if (cadena.trim().equals("*")) { // Cliente se desconecta cuando envía un *
                    // Liberar el socket de la tabla y reducir el número de conexiones activas
                    int index = encontrarSocketIndex(socket);  // Encontrar el índice del socket en la tabla
                    if (index != -1) {
//                        comun.eliminarConexion(index);  // Eliminar la conexión de la tabla
                        comun.setConexiones(comun.getConexiones() - 1); // Reducir el contador de conexiones
                        comun.setActuales(comun.getActuales() - 1); // Reducir el contador de conexiones activas
                    }
                    System.out.println("Número de conexiones actuales: " + comun.getActuales());
                    break; // Para salir del bucle
                }
                comun.setMensajes(comun.getMensajes() + cadena + "\n");
                EnviarMensajeTodos(comun.getMensajes());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        // Se cierra el socket del cliente
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Envia los mensajes del Chat a los clientes
    private void EnviarMensajeTodos(String texto) {
        int i;

        // Recorremos la tabla de sockets para enviarles los mensajes
        for (i = 0; i < comun.getConexiones(); i++) {
            Socket s1 = comun.getElementoTabla(i);
            if (s1 != null && !s1.isClosed()) {
                try {
                    DataOutputStream fsalida2 = new DataOutputStream(s1.getOutputStream());
                    fsalida2.writeUTF(texto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método para encontrar el índice de un socket en la tabla
    private int encontrarSocketIndex(Socket s) {
        for (int i = 0; i < comun.getConexiones(); i++) {
            if (comun.getElementoTabla(i) != null && comun.getElementoTabla(i).equals(s)) {
                return i; // Devolver el índice si se encuentra el socket
            }
        }
        return -1; // Si no se encuentra el socket
    }
}
