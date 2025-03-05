package Act4_05;

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;

import java.io.BufferedReader;
import java.io.IOException;

public class CorreoExterno {
    public static void main(String[] args) {
        // Datos del servidor y credenciales de acceso
        String server = "pop.gmail.com", username = "angelapedreralopez@gmail.com", password = "lxgaqqvrovmgavjt";
        int puerto = 995;  // Puerto de conexión para POP3S (secure)

        // Creamos una instancia de POP3SClient para una conexión segura con el servidor
        POP3SClient pop3 = new POP3SClient(true);
        try {
            // Intentamos conectar con el servidor POP3
            pop3.connect(server, puerto);
            System.out.println("Conexión realizada al servidor POP3 " + server);
            System.out.println("Respuesta del servidor: " + pop3.getReplyString());

            // Intentamos iniciar sesión con las credenciales proporcionadas
            if (!pop3.login(username, password)) {
                System.err.println("Error al hacer login");
                System.out.println("Respuesta del servidor: " + pop3.getReplyString());
            } else {
                // Si la sesión es exitosa, recuperamos la lista de mensajes
                System.out.println("Respuesta del servidor: " + pop3.getReplyString());
                POP3MessageInfo[] men = pop3.listMessages();

                if (men == null) {
                    // Si no se pueden recuperar mensajes, mostramos un error
                    System.out.println("Imposible recuperar mensajes.");
                    System.out.println("Respuesta del servidor: " + pop3.getReplyString());
                } else {
                    // Si se recuperaron mensajes, mostramos el número de mensajes disponibles
                    System.out.println("N° de mensajes: " + men.length);
                    System.out.println("Respuesta del servidor: " + pop3.getReplyString());
                }

                // Llamamos al método que recupera el contenido de los mensajes
                System.out.println("\nMensaje:");
                Recuperarmensajes(men, pop3);

                // Llamamos al método que recupera las cabeceras de los mensajes
                System.out.println("\nCabecera:");
                Recuperarcabeceras(men, pop3);

                // Finalizamos la sesión con el servidor POP3
                pop3.logout();
                System.out.println("Respuesta del servidor: " + pop3.getReplyString());
            }

            // Finalmente, nos desconectamos del servidor
            pop3.disconnect();
            System.out.println("Respuesta del servidor: " + pop3.getReplyString());

        } catch (IOException e) {
            // En caso de error, se muestra la excepción y el stack trace
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);  // Terminamos la ejecución del programa
    }

    // Método para recuperar los mensajes de correo
    private static void Recuperarmensajes(POP3MessageInfo[] men, POP3Client pop3) throws IOException {
        for (int i = 0; i < men.length; i++) {
            System.out.println("Mensaje: " + (i + 1));
            POP3MessageInfo msginfo = men[i];  // Recuperamos un mensaje de la lista

            // Mostramos información básica sobre el mensaje
            System.out.println("Identificador: " + msginfo.identifier +
                    ", Number: " + msginfo.number +
                    ", Tamaño: " + msginfo.size);

            // Realizamos una prueba de obtener un identificador único de un mensaje
            System.out.println("Prueba de listUniqueIdentifier: ");
            POP3MessageInfo pmi = pop3.listUniqueIdentifier(i + 1);  // Obtenemos un mensaje específico
            System.out.println("\tIdentificador: " + pmi.identifier +
                    ", Number: " + pmi.number +
                    ", Tamaño: " + pmi.size);
            System.out.println("Respuesta del servidor: " + pop3.getReplyString());
        }
    }

    // Método para recuperar las cabeceras de los mensajes
    private static void Recuperarcabeceras(POP3MessageInfo[] men, POP3Client pop3) throws IOException {
        for (int i = 0; i < men.length; i++) {
            System.out.println("Mensaje: " + (i + 1));
            POP3MessageInfo msginfo = men[i];

            System.out.println("Cabecera del mensaje:");
            // Recuperamos solo las primeras líneas del mensaje (cabeceras)
            BufferedReader reader = (BufferedReader) pop3.retrieveMessageTop(msginfo.number, 0);
            System.out.println("Respuesta del servidor: " + pop3.getReplyString());

            // Leemos y mostramos las cabeceras del mensaje
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea.toString());
            }
            reader.close();  // Cerramos el BufferedReader después de leer las cabeceras
        }
    }
}
