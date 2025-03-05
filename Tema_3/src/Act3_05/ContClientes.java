package Act3_05;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ContClientes  extends Thread {
    private Socket cliente;
    private int numeroCliente;

    public ContClientes(Socket cliente, int numeroCliente) {
        this.cliente = cliente;
        this.numeroCliente = numeroCliente;
    }

    @Override
    public void run() {
        try {
            // Crear flujo de salida para enviar mensaje al cliente
            OutputStream salida = cliente.getOutputStream();
            DataOutputStream flujoSalida = new DataOutputStream(salida);

            // Enviar mensaje al cliente con su número
            String mensaje = "Hola, eres el cliente número " + numeroCliente;
            flujoSalida.writeUTF(mensaje);
            System.out.println("Mensaje enviado al cliente " + numeroCliente + ": " + mensaje);

            // Cerrar flujo de salida y socket
            flujoSalida.close();
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}