package actividad2_7;

public class Sincroniza implements Runnable {
    private static int contador = 0;

    public void run() {
        synchronized (Sincroniza.class) {
            for (int i = 0; i < 5000; i++) {
                contador++;
            }
        }
    }

    public static void main(String[] args) {
        Sincroniza h1 = new Sincroniza();
        Sincroniza h2 = new Sincroniza();
        Sincroniza h3 = new Sincroniza();
        Sincroniza h4 = new Sincroniza();
        Sincroniza h5 = new Sincroniza();

        h1.run();
        h2.run();
        h3.run();
        h4.run();
        h5.run();

        System.out.println("Contador final con sincronización: " + contador);
    }
}
