package actividad2_2.Act2;

public class HolaMundoMensaje implements Runnable {
    private String mensaje;

    // Constructor que recibe una cadena
    public HolaMundoMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        // Obtener el ID del hilo actual
        long threadId = Thread.currentThread().getId();

        // Tiempo de espera proporcional al identificador del hilo
        try {
            Thread.sleep(threadId * 100); // Tiempo de espera basado en el ID del hilo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Hola mundo, " + mensaje + " desde el hilo " + threadId);
    }
}

