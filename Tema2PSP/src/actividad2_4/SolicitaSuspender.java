package actividad2_4;

public class SolicitaSuspender {
    private boolean suspender;

    public synchronized void suspende(boolean b) {
        suspender = b; // Cambiar el estado de suspensión
        notifyAll(); // Notificar a los hilos que están esperando
    }

    public synchronized void reanudar() {
        suspender = false; // Cambiar el estado a "no suspendido"
        notifyAll(); // Notificar a los hilos
    }

    public synchronized void esperarParaReanudar() throws InterruptedException {
        while (suspender) { // Esperar mientras esté suspendido
            wait(); // Espera hasta que se reanude
        }
    }
}
