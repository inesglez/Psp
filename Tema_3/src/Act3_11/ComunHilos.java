package Act3_11;

import java.net.Socket;

public class ComunHilos {

    int CONEXIONES;
    int ACTUALES;
    int MAXIMO;
    Socket[] tabla;
    String mensajes;

    public ComunHilos(int max, int actuales, int conexiones, Socket[] tabla) {
        MAXIMO = max;
        ACTUALES = actuales;
        CONEXIONES = conexiones;
        this.tabla = new Socket[MAXIMO];  // Inicialización del arreglo de sockets
        mensajes = "";
    }

    public ComunHilos() {
        // Constructor vacío, puedes añadir cualquier lógica si es necesario
    }

    public int getMax() {
        return MAXIMO;
    }

    public void setMax(int max) {
        MAXIMO = max;
    }

    public int getConexiones() {
        return CONEXIONES;
    }

    public synchronized void setConexiones(int conexiones) {
        this.CONEXIONES = conexiones;
    }

    public int getActuales() {
        return ACTUALES;
    }

    public synchronized void setActuales(int actuales) {
        this.ACTUALES = actuales;
    }

    public synchronized Socket getElementoTabla(int i) {
        if (i >= 0 && i < MAXIMO) {
            return tabla[i];
        }
        return null;
    }

    public synchronized void addTabla(Socket s, int i) {
        if (i < MAXIMO) {  // Aseguramos que no se exceda el límite de conexiones
            tabla[i] = s;
        } else {
            System.out.println("No se pueden aceptar más conexiones.");
        }
    }

    public String getMensajes() {
        return mensajes;
    }

    public synchronized void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }
}
