package actividad2_2.Act2;

public class Principal {
    public static void main(String[] args) {
        // Crear y lanzar 5 hilos
        for (int i = 1; i <= 5; i++) {
            HolaMundoMensaje tarea = new HolaMundoMensaje("Soy Ines " + i);

            // Crear un nuevo hilo pasándole la tarea
            Thread hilo = new Thread(tarea);

            // Iniciar el hilo
            hilo.start();
        }
    }
}
