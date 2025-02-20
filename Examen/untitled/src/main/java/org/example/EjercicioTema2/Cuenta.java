package org.example.EjercicioTema2;
//No funciona porque no esta terminado

public class Cuenta {
    private int saldo;

    Cuenta (int s) {saldo = s;} //Inicializa el saldo actual
    int getSaldo() {return saldo;} // devuelve el saldo
    public void restar (int cantidad){
        saldo = saldo - cantidad;
    }

    void RetirarDinero (int cant, String nom) {
        if (getSaldo() >= cant) {
            System.out.println(nom + ": Se va a retirar saldo ( actual es: " + getSaldo() + ")");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            restar(cant);

            System.out.println("\t" + nom + " retira => " + cant +
                    "Actual (" + getSaldo() + ")");
        } else {
            System.out.println(nom +
                    " No puede retirar dinero, no hay saldo (" + getSaldo() + ")");
        }
        if (getSaldo() <= 0) {
            System.out.println("Saldo Negativo => " + getSaldo());
        }
    }
}
