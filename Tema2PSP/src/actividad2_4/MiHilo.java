package actividad2_4;

import java.util.Scanner;

class MiHilo extends Thread {
    private SolicitaSuspender suspender = new SolicitaSuspender(); // Control de suspensión del hilo
    private boolean fin = false; // Control para finalizar el hilo

    public void run() {
        try {
            while (!fin) { // Trabajar hasta que se indique el fin
                System.out.println("Hilo en ejecución...");
                suspender.esperarParaReanudar(); // Comprobar si el hilo debe suspenderse
                Thread.sleep(1000); // Simular trabajo con una pausa
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void suspenderHilo() {
        suspender.suspende(true); // Llamada para suspender el hilo
    }

    public void reanudarHilo() {
        suspender.reanudar(); // Llamada para reanudar el hilo
    }

    public void finalizarHilo() {
        fin = true; // Indicar que el hilo debe finalizar
    }
}