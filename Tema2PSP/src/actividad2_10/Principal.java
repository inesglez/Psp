package actividad2_10;

public class Principal {
    public static void main(String[] args) {
        Cola cola = new Cola();
        Productor productor = new Productor(cola,1);
        Consumidor  consumidor = new Consumidor(cola,2);
        productor.start();
        consumidor.start();
    }
}
