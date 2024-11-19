package actividad2_9;

public class Productor extends Thread {
    private Cola cola;
    private int n;
    public Productor(Cola cola, int n) {
        this.cola = cola;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            cola.put(i);
            System.out.println("Productor " + i + " encolado");
            try{
                sleep(100);
            } catch (InterruptedException e) { }
        }
    }
}