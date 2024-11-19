package actividad2_6;

public class HiloPrioridad extends Thread {
    private int c = 0;            // Contador para contar las iteraciones
    private boolean stopHilo = false;  // Controla cuando detener el hilo

    // Constructor que asigna el nombre al hilo
    public HiloPrioridad(String nombre) {
        super(nombre);  // Asigna el nombre al hilo
    }

    // Devuelve el valor del contador
    public int getContador() {
        return c;
    }

    // Detiene el hilo cambiando la bandera 'stopHilo' a true
    public void pararHilo() {
        stopHilo = true;
    }

    // Método que ejecuta el hilo
    public void run() {
        while (!stopHilo) {  // El hilo sigue ejecutándose hasta que se le indique parar
            try {
                Thread.sleep(2);  // Pausa el hilo por 2 milisegundos
            } catch (Exception e) {}  // Ignora cualquier excepción
            c++;
        }
        System.out.println("Fin hilo " + this.getName());
    }
}
