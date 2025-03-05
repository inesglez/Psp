package Act3_10;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloServidorAdivina extends Thread {
    ObjectInputStream entrada;
    ObjectOutputStream salida;
    Socket socket = null;
    ObjetoCompartido objeto;
    int identificador;
    int intentos = 0;

    public HiloServidorAdivina(Socket s, ObjetoCompartido objeto, int id) {
        this.socket = s;
        this.objeto = objeto;
        this.identificador = id;
        try {
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S en HiloServidor");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("=> Cliente conectado: " + identificador);

        // Preparar primer envío al cliente
        Datos datos = new Datos("Adivina un NÚMERO ENTRE 1 Y 25", intentos, identificador);
        if (objeto.seAcabo()) {
            datos.setCadena("LO SENTIMOS, EL JUEGO HA TERMINADO, HAN ADIVINADO EL Nº.");
            datos.setJuega(false); // Ya no tiene que jugar
        }
        try {
            salida.reset();
            salida.writeObject(datos);
        } catch (IOException e) {
            System.out.println("Error en el Hilo al realizar " + "el primer envio del jugador: " + identificador);
            return;
        }

        while(!objeto.seAcabo() || !(datos.getIntentos() == 5)) {
            int numeroCli = 0;
            try {
                // Recibir datos del cliente
                Datos d = (Datos) entrada.readObject();
                numeroCli = Integer.parseInt(d.getCadena());
            } catch (IOException e) {
                System.out.println("Error en el Hilo al leer del jugador: " + identificador);
                break;
            } catch (NumberFormatException n) {
                System.out.println("El jugador: " + identificador + " se a desconectado");
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

            // Jugar el número
            String cad = objeto.nuevaJugada(identificador, numeroCli);
            intentos++;

            datos = new Datos(cad, intentos, identificador);

            if (objeto.seAcabo()) {
                datos.setJuega(false); // no tiene que seguir jugando
                if (identificador == objeto.getGanador()) {
                    datos.setGana(true);
                }
            }

            try {
                // Enviar datos al cliente
                salida.reset();
                salida.writeObject(datos);
            } catch (IOException n1) {
                System.out.println("Error escribiendo en flujo de salida del jugador: " + identificador + " * " + n1.getMessage());
                break;
            } catch (NullPointerException n) {
                System.out.println("El jugador " + identificador + " se ha desconectado.");
                break;
            }
        }

        if (objeto.seAcabo()) {
            System.out.println("EL JUEGO SE HA ACABADO.....");
            System.out.println("\t=> Desconecta: " + identificador);
        }

        try {
            salida.close();
            entrada.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error en Hilo al cerrar cliente: " + identificador);
        }
    }
}
