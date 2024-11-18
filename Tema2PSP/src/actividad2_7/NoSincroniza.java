package actividad2_7;

public class NoSincroniza  extends Thread {
    private static int contador = 0;

    public void run() {
        for (int i = 0; i < 5000; i++) {
            contador++;
        }
    }

    public static void main(String[] args) {
        NoSincroniza h1 = new NoSincroniza();
        NoSincroniza h2 = new NoSincroniza();
        NoSincroniza h3 = new NoSincroniza();
        NoSincroniza h4 = new NoSincroniza();
        NoSincroniza h5 = new NoSincroniza();

        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();

        System.out.println("Contador final sin sincronización: " + contador);
    }
}

