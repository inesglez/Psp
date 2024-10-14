package actividad2_1;

class Tac extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("TAC");
            try {
                // Pausa de medio segundo
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
