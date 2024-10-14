package actividad2_1.Act1;

public class HolaMundoPrincipal {
    public static void main(String[] args) {
        // Crear y lanzar 5 hilos
        for (int i = 0; i < 5; i++) {
            HolaMundo hilo = new HolaMundo();
            hilo.start();  // Iniciar cada hilo
        }
    }
}
