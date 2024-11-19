package actividad2_9;

public class Principal {
    public static void main(String[] args) {

        Cola cola = new Cola();
        Productor p = new Productor(cola, 1);
        Consumidor c = new Consumidor(cola, 1);

        p.start();
        c.start();

    }
}
/*
 * No se obtiene la salida correcta porque los hilos no están sincronizados.
 * Si no usamos `synchronized` en los métodos `put()` y `get()` de la clase `Cola`,
 * el productor y el consumidor pueden acceder al recurso al mismo tiempo, causando problemas.
 * Usar `sleep()` no arregla el problema, solo ralentiza la ejecución,
 * pero no garantiza que los hilos estén coordinados.
 * La solución es sincronizar los métodos `put()` y `get()` para que los hilos no se interfieran.
 */