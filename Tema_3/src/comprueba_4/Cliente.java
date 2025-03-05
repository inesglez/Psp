package comprueba_4;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Leer el identificador asignado por el servidor
            int clientId = in.readInt();
            System.out.println("SOY EL CLIENTE: " + clientId);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String idProfesor;
            while (true) {
                System.out.print("Introduce identificador a consultar: ");
                idProfesor = reader.readLine();
                out.writeUTF(idProfesor);

                if (idProfesor.equals("*")) {
                    break;
                }

                String response = in.readUTF();
                while (response != null) {
                    System.out.println(response);
                    response = in.readUTF();
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

