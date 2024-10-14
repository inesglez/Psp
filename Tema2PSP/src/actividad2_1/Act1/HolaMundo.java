package actividad2_1.Act1;

public class HolaMundo extends Thread {
    @Override
    public void run() {
        // Visualizar mensaje con identificador del hilo
        System.out.println("Hola mundo desde el hilo " + this.getId());
    }
}

