package actividad2_8;

import java.util.Random;

public class Saldo {
    private double saldo; // Este es el atributo donde guardamos el saldo

    // Constructor que permite asignar un saldo inicial cuando creamos un objeto de tipo Saldo
    public Saldo(double saldoInicial) {
        this.saldo = saldoInicial; // Asignamos el valor recibido al saldo
    }

    // Este método nos da el saldo actual, pero con una pausa aleatoria para simular un retraso
    public synchronized double obtenerSaldo() {
        try {
            Thread.sleep(new Random().nextInt(1000)); // Pausa aleatoria entre 0 y 1000 ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Si la pausa se interrumpe, la marcamos
        }
        return saldo; // Devolvemos el saldo
    }

    // Este método cambia el saldo a un nuevo valor y también incluye una pausa aleatoria
    public synchronized void restablecerSaldo(double nuevoSaldo) {
        try {
            Thread.sleep(new Random().nextInt(1000)); // Pausa aleatoria entre 0 y 1000 ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Si la pausa se interrumpe, la marcamos
        }
        this.saldo = nuevoSaldo; // Actualizamos el saldo con el nuevo valor
    }

    // Este método permite añadir una cantidad al saldo, mostrando el saldo antes y después del cambio
    public synchronized void añadirCantidad(double cantidad, String quienAñade) {
        double saldoAntes = this.saldo; // Guardamos el saldo antes de hacer el cambio
        this.saldo += cantidad; // Sumamos la cantidad al saldo
        double saldoDespues = this.saldo; // Guardamos el saldo después de sumar
        // Mostramos en consola quién añadió la cantidad y el cambio de saldo
        System.out.println(quienAñade + " añade " + cantidad + " al saldo.");
        System.out.println("Saldo antes: " + saldoAntes + ", saldo después: " + saldoDespues);
    }
}
