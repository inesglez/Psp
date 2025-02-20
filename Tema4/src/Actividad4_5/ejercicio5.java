package actividad4_5;

import java.io.IOException;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;

public class Ejercicio5 {
    public static void main(String[] args) {
        // Datos del servidor y credenciales 
        String server = "pop.mailserver.com";
        String username = "usuario@gmail.com";
        String password = "contraseña";
        int puerto = 995;

        POP3SClient pop3 = new POP3SClient(true);
        try {
            pop3.connect(server, puerto);
            System.out.println("Conexión establecida con el servidor");

            // Intentamos iniciar sesión con las credenciales proporcionadas
            if (!pop3.login(username, password)) {
                System.out.println("Error al iniciar sesión en el servidor POP3.");
            } else {
                // Obtenemos la lista de mensajes en el servidor
                POP3MessageInfo[] mensajes = pop3.listMessages();

                if (mensajes == null) {
                    System.out.println("No se pudieron listar los mensajes.");
                } else {
                    System.out.println("Cantidad de mensajes: " + mensajes.length);
                    recuperarMensajes(mensajes, pop3);
                }
                pop3.logout();
            }
            pop3.disconnect();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    // Método para recuperar detalles de los mensajes en el servidor
    private static void recuperarMensajes(POP3MessageInfo[] mensajes, POP3SClient pop3) throws IOException {
        for (int i = 0; i < mensajes.length; i++) {
            System.out.println("\nMensaje " + (i + 1));
            POP3MessageInfo msgInfo = mensajes[i];

            System.out.println("ID: " + msgInfo.identifier);
            System.out.println("Número: " + msgInfo.number);
            System.out.println("Tamaño: " + msgInfo.size + " bytes");

            // Intentamos obtener un identificador único del mensaje
            POP3MessageInfo pmi = pop3.listUniqueIdentifier(i + 1);
            if (pmi != null) {
                System.out.println("Identificador único: " + pmi.identifier);
            } else {
                System.out.println("No se pudo obtener el identificador único.");
            }
        }
    }
}
