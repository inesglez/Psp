package actividad2_7;

public class Sincroniza implements Runnable {
    private static int contador = 0; // Contador estático compartido entre todos los hilos

    // Método que se ejecuta cuando el hilo comienza a correr
    public void run() {
        // Sincronización para asegurar que solo un hilo acceda al bloque de código a la vez
        synchronized (Sincroniza.class) {
            // Incrementa el contador 5000 veces
            for (int i = 0; i < 5000; i++) {
                contador++;
            }
        }
    }

    // Método principal
    public static void main(String[] args) {
        // Crear cinco hilos (objetos de la clase Sincroniza)
        Sincroniza h1 = new Sincroniza();
        Sincroniza h2 = new Sincroniza();
        Sincroniza h3 = new Sincroniza();
        Sincroniza h4 = new Sincroniza();
        Sincroniza h5 = new Sincroniza();

        // Ejecutar cada hilo, lo que invoca el método run()
        h1.run();h2.run();h3.run();h4.run();h5.run();

        System.out.println("Contador final con sincronización: " + contador);
    }
}
