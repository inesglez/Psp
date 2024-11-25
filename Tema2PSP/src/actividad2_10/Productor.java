package actividad2_10;

public class Productor extends Thread {
    private Cola cola;
    private int n;
    private boolean continuar = true;

    public void detener() {
        continuar = false;
    }

    public Productor(Cola cola, int n) {
        this.cola = cola;
        this.n = n;
    }

    public void dormir(){
        try {
            sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (continuar) {
            try {
                cola.put("PING");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            dormir();
            try {
                cola.put("PONG");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        dormir();
    }
}