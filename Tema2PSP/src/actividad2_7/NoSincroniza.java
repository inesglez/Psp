package actividad2_7;

public class NoSincroniza extends Thread {
    private static int contador = 0; // Contador estático compartido entre todos los hilos

    // Método que se ejecuta cuando el hilo comienza a correr
    public void run() {
        // Incrementa el contador 5000 veces
        for (int i = 0; i < 5000; i++) {
            contador++;
        }
    }

    // Método principal
    public static void main(String[] args) {
        // Crear cinco hilos
        NoSincroniza h1 = new NoSincroniza();
        NoSincroniza h2 = new NoSincroniza();
        NoSincroniza h3 = new NoSincroniza();
        NoSincroniza h4 = new NoSincroniza();
        NoSincroniza h5 = new NoSincroniza();

        // Lanzar los hilos con start(), lo que hace que se ejecuten en paralelo
        h1.start();h2.start();h3.start();h4.start();h5.start();

        // Imprimir el valor final del contador después de que los hilos han terminado
        // **Nota importante**: El contador probablemente no mostrará el valor correcto aquí debido a la falta de sincronización
        System.out.println("Contador final sin sincronización: " + contador);
    }
}
