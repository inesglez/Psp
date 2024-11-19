package actividad2_9;

public class Cola {
    private int numero;
    private boolean vacio = false;

    public int get() {
        if (vacio) {
            vacio = false;
            return numero;
        }
        return -1;
    }
    public void put(int valor){
        numero = valor;
        vacio = true;
    }
}