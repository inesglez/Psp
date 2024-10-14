package actividad2_2;

public class HolaMundo  implements Runnable {
    @Override
    public void run() {
        System.out.println("Hola mundo desde el hilo " + Thread.currentThread().getId());
    }
}
