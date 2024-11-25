package actividad2_10;

public class Consumidor extends Thread {
    private Cola cola;
    private int n;
    boolean continuar = true;

    public void detener() {
        continuar = false;
    }

    public Consumidor(Cola cola, int n) {
        this.cola = cola;
        this.n = n;
    }

    @Override
    public void run() {
        while (continuar) {
            try {
                System.out.println(cola.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                sleep(310);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}