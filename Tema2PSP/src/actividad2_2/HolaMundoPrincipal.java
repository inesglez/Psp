package actividad2_2;

public class HolaMundoPrincipal {
    public static void main(String[] args) {
        // Crear y lanzar 5 hilos
        for (int i = 0; i < 5; i++) {
            // Crear una instancia de HolaMundo
            HolaMundo tarea = new HolaMundo();

            // Crear un nuevo hilo pasándole la tarea
            Thread hilo = new Thread(tarea);

            // Iniciar el hilo
            hilo.start();
        }
    }
}
