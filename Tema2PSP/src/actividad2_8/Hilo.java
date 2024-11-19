package actividad2_8;

public class Hilo extends Thread {
    private Saldo saldo;  // Objeto Saldo compartido entre los hilos
    private double cantidad;  // Cantidad que el hilo va a añadir al saldo
    private String nombreHilo; // Nombre del hilo, para mostrar en la consola

    // Constructor: Inicializa el objeto 'Saldo', la cantidad a añadir y el nombre del hilo
    public Hilo(Saldo saldo, double cantidad, String nombreHilo) {
        this.saldo = saldo;        // Asignamos el objeto 'Saldo' que compartirán los hilos
        this.cantidad = cantidad;  // La cantidad que este hilo va a añadir al saldo
        this.nombreHilo = nombreHilo;
    }

    // Método que ejecuta el hilo. Es llamado cuando se inicia el hilo con start()
    @Override
    public void run() {
        // Llama al método 'añadirCantidad' de la clase Saldo para modificar el saldo
        saldo.añadirCantidad(cantidad, nombreHilo);
    }
}
