package actividad2_8;

public class Principal {
    public static void main(String[] args) throws InterruptedException {
        // Crear un objeto 'Saldo' con un valor inicial de 1000.0
        Saldo saldo = new Saldo(1000.0);

        // Mostrar el saldo inicial
        System.out.println("Saldo inicial = " + saldo.obtenerSaldo());

        // Crear 3 hilos que compartirán el objeto 'Saldo' y cada uno añadirá una cantidad diferente
        Hilo hilo1 = new Hilo(saldo, 200.0, "Hilo 1"); // Hilo 1 añade 200
        Hilo hilo2 = new Hilo(saldo, 150.0, "Hilo 2"); // Hilo 2 añade 150
        Hilo hilo3 = new Hilo(saldo, 300.0, "Hilo 3"); // Hilo 3 añade 300

        // Lanzamos los hilos para que empiecen a ejecutarse
        hilo1.start();
        hilo2.start();
        hilo3.start();

        // Esperamos a que todos los hilos terminen de ejecutarse antes de continuar
        hilo1.join(); // Espera que termine hilo1
        hilo2.join(); // Espera que termine hilo2
        hilo3.join(); // Espera que termine hilo3

        // Mostrar el saldo final después de que los hilos hayan añadido sus cantidades
        System.out.println("Saldo final = " + saldo.obtenerSaldo());

        // Restablecer el saldo a 1000.0
        saldo.restablecerSaldo(1000.0);

        // Mostrar el saldo después de restablecerlo a su valor original
        System.out.println("Saldo final después de haber sido reestablecido = " + saldo.obtenerSaldo());
    }

}
