package actividad2_1;

public class TicTac {
    public static void main(String[] args) {
        // Crear los hilos
        Tic tic = new Tic();
        Tac tac = new Tac();

        // Iniciar los hilos
        tic.start();
        tac.start();
    }
}

