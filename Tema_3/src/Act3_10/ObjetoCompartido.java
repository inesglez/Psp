package Act3_10;

public class ObjetoCompartido {
    private int numero; // Número a adivinar
    private boolean acabo; // True cuando se haya terminado el juego
    private int ganador; // Jugador ganador

    public ObjetoCompartido(int numero) {
        this.numero = numero;
        this.acabo = false;
    }

    public boolean seAcabo() {
        return acabo;
    }

    public int getGanador() {
        return ganador;
    }

    public synchronized String nuevaJugada(int jugador, int suNumero) {
        String cad = "";
        if (!seAcabo()) {
            if (suNumero > numero) { // es mayor
                cad = "Número demasiado grande";
            }
            if (suNumero < numero) { // es menor
                cad = "Número demasiado bajo";
            }
            if (suNumero == numero) { // ha acertado
                cad = "Jugador" + jugador + " gana, adivinó el número: " + numero;
                acabo = true;
                ganador = jugador;
            }
        } else {
            cad = "Jugador " + ganador + " adivinó el número: " + numero;
        }
        return cad;
    }
}
