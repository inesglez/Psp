package actividad2_6;

public class EjPrioridad2 {
    public static void main(String[] args) {
        // Crear tres hilos con diferentes nombres
        HiloPrioridad h1 = new HiloPrioridad("Hilo1");
        HiloPrioridad h2 = new HiloPrioridad("Hilo2");
        HiloPrioridad h3 = new HiloPrioridad("Hilo3");

        // Establecer la prioridad de cada hilo:
        // Hilo 1 con prioridad normal (valor por defecto)
        h1.setPriority(Thread.NORM_PRIORITY);
        // Hilo 2 con la máxima prioridad
        h2.setPriority(Thread.MAX_PRIORITY);
        // Hilo 3 con la mínima prioridad
        h3.setPriority(Thread.MIN_PRIORITY);

        // Iniciar la ejecución de los hilos
        h1.start();
        h2.start();
        h3.start();

        try {
            // El hilo principal espera 10 segundos para permitir que los hilos terminen su ejecución
            Thread.sleep(10000);
        } catch (Exception e) {
            // Manejo de excepciones, si ocurre algún error durante el sueño
        }

        // Detener los hilos después de 10 segundos
        h1.pararHilo();
        h2.pararHilo();
        h3.pararHilo();

        // Mostrar el contador de cada hilo después de su ejecución
        System.out.println("Hilo 2 (Máxima Prioridad): " + h2.getContador());
        System.out.println("Hilo 1 (Prioridad Normal): " + h1.getContador());
        System.out.println("Hilo 3 (Mínima Prioridad): " + h3.getContador());
    }
}
