package actividad2_4;

import java.util.Scanner;

public class Actividad2_4 {
    public static void main(String[] args) {
        MiHilo hilo = new MiHilo();
        hilo.start(); // Iniciar el hilo

        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Introduce 's' para suspender, 'r' para reanudar, 'f' para finalizar:");
            input = sc.nextLine();

            if (input.equals("s")) {
                hilo.suspenderHilo(); // Suspender el hilo
            } else if (input.equals("r")) {
                hilo.reanudarHilo(); // Reanudar el hilo
            } else if (input.equals("f")) {
                hilo.finalizarHilo(); // Finalizar el hilo
                break;
            } else {
                System.out.println("Comando no reconocido."); //En el caso que se añadiera algo diferente a s,r o f
            }
        }

        sc.close();
        try {
            hilo.join(); // Esperar a que el hilo finalice
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
