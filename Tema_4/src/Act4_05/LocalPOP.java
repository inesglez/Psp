package Act4_05;

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;

import java.io.BufferedReader;
import java.io.IOException;

public class LocalPOP {
    public static void main(String[] args) {
        String server = "localhost", username = "usu1", password = "usuario";
        int puerto = 110;

        POP3Client pop3 = new POP3Client();
        try {
            // Nos conectamos al servidor POP3
            pop3.connect(server, puerto);
            System.out.println("Conexión realizada al servidor POP3 " + server);
            System.out.println("Respuesta del servidor: " + pop3.getReplyString());

            // Iniciamos sesión con las credenciales proporcionadas
            if (!pop3.login(username, password)) {
                System.err.println("Error al hacer login");
                System.out.println("Respuesta del servidor: " + pop3.getReplyString());
            } else {
                // Si el login es exitoso, obtenemos la lista de mensajes
                System.out.println("Respuesta del servidor: " + pop3.getReplyString());
                POP3MessageInfo[] men = pop3.listMessages();

                // Verificamos si se pudo recuperar la lista de mensajes
                if (men == null) {
                    System.out.println("Imposible recuperar mensajes.");
                    System.out.println("Respuesta del servidor: " + pop3.getReplyString());
                } else {
                    System.out.println("N° de mensajes: " + men.length);
                    System.out.println("Respuesta del servidor: " + pop3.getReplyString());
                }

                // Recuperar los mensajes completos
                System.out.println("\nMensaje:");
                Recuperarmensajes(men, pop3);

                // Recuperar solo las cabeceras de los mensajes
                System.out.println("\nCabecera:");
                Recuperarcabeceras(men, pop3);

                // Finalizamos la sesión
                pop3.logout();
                System.out.println("Respuesta del servidor: " + pop3.getReplyString());
            }

            // Nos desconectamos del servidor POP3
            pop3.disconnect();
            System.out.println("Respuesta del servidor: " + pop3.getReplyString());

        } catch (IOException e) {
            // Capturamos errores de entrada/salida (por ejemplo, conexión fallida)
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);  // Finalizamos el programa
    }

    // Método para recuperar y mostrar los mensajes completos
    private static void Recuperarmensajes(POP3MessageInfo[] men, POP3Client pop3) throws IOException {
        for (int i = 0; i < men.length; i++) {
            System.out.println("Mensaje: " + (i + 1));
            POP3MessageInfo msginfo = men[i]; // Accedemos a la información de cada mensaje
            System.out.println("Identificador: " + msginfo.identifier +
                    ", Number: " + msginfo.number +
                    ", Tamaño: " + msginfo.size);

            // Comprobamos el identificador único de cada mensaje
            System.out.println("Prueba de listUniqueIdentifier: ");
            POP3MessageInfo pmi = pop3.listUniqueIdentifier(i + 1); // Obtenemos el identificador único
            System.out.println("\tIdentificador: " + pmi.identifier +
                    ", Number: " + pmi.number +
                    ", Tamaño: " + pmi.size);
            System.out.println("Respuesta del servidor: " + pop3.getReplyString());
        }
    }

    // Método para recuperar solo las cabeceras de los mensajes
    private static void Recuperarcabeceras(POP3MessageInfo[] men, POP3Client pop3) throws IOException {
        for (int i = 0; i < men.length; i++) {
            System.out.println("Mensaje: " + (i + 1));
            POP3MessageInfo msginfo = men[i];

            // Obtenemos solo las cabeceras del mensaje
            System.out.println("Cabecera del mensaje:");
            BufferedReader reader = (BufferedReader) pop3.retrieveMessageTop(msginfo.number, 0);
            System.out.println("Respuesta del servidor: " + pop3.getReplyString());

            // Leemos las cabeceras línea por línea
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea.toString());
            }
            reader.close(); // Cerramos el BufferedReader al finalizar la lectura
        }
    }
}
