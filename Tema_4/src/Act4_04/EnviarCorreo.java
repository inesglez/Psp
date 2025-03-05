package Act4_04;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.io.IOException;
import java.io.Writer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class EnviarCorreo {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, InvalidKeyException, InvalidKeySpecException {
        Scanner scanner = new Scanner(System.in);

        // Solicitar datos al usuario
        System.out.print("Introduce servidor SMTP: ");
        String server = scanner.nextLine();

        System.out.print("¿Necesita negociación TLS (S/N)?: ");
        boolean usaTLS = scanner.nextLine().equalsIgnoreCase("S");

        System.out.print("Introduce usuario: ");
        String userName = scanner.nextLine();

        System.out.print("Introduce password: ");
        String password = scanner.nextLine();

        System.out.print("Introduce puerto: ");
        int puerto = Integer.parseInt(scanner.nextLine());

        System.out.print("Introduce correo del remitente: ");
        String remitente = scanner.nextLine();

        System.out.print("Introduce correo destinatario: ");
        String destinatario = scanner.nextLine();

        System.out.print("Introduce asunto: ");
        String asunto = scanner.nextLine();

        // Leer mensaje línea por línea hasta que el usuario introduzca '*'
        StringBuilder mensaje = new StringBuilder();
        System.out.println("Introduce el mensaje (finaliza con *):");
        while (true) {
            String linea = scanner.nextLine();
            if (linea.equals("*")) break;
            mensaje.append(linea).append("\n");
        }

        // No permitir que haya mensajes vacíos
        if (mensaje.toString().trim().isEmpty()) {
            System.out.println("Error: No se puede enviar un mensaje vacío.");
            System.exit(1);
        }

        // Se crea cliente SMTP seguro
        AuthenticatingSMTPClient cliente = new AuthenticatingSMTPClient();

        try {
            int respuesta;

            // Creación de la clave para establecer un canal seguro
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(null, null);
            KeyManager km = kmf.getKeyManagers()[0];

            // Conectarse al servidor SMTP
            cliente.connect(server, puerto);
            System.out.println("1 - " + cliente.getReplyString());

            cliente.setKeyManager(km); // Establecer clave para la comunicación segura

            respuesta = cliente.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(respuesta)) {
                cliente.disconnect();
                System.err.println("CONEXIÓN CERRADA");
                System.exit(1);
            }

            // Enviar comando EHLO para identificación
            cliente.ehlo(server);
            System.out.println("2 - " + cliente.getReplyString());

            // Si se requiere negociación TLS, se ejecuta STARTTLS
            if (usaTLS) {
                if (cliente.execTLS()) {
                    System.out.println("3 - " + cliente.getReplyString());
                } else {
                    System.out.println("Error: No se pudo establecer negociación TLS.");
                    System.exit(1);
                }
            } else {
                System.out.println("Negociación TLS no requerida.");
            }

            // Autenticación con el servidor
            if (cliente.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN, userName, password)) {
                System.out.println("4 - " + cliente.getReplyString());

                // Se crea la cabecera del correo
                SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destinatario, asunto);

                // Configurar remitente y destinatario
                cliente.setSender(remitente);
                cliente.addRecipient(destinatario);
                System.out.println("5 - " + cliente.getReplyString());

                // Enviar DATA (contenido del mensaje)
                Writer writer = cliente.sendMessageData();
                if (writer == null) {
                    System.out.println("Error: Fallo al enviar DATA.");
                    System.exit(1);
                }

                writer.write(cabecera.toString()); // Escribir cabecera
                writer.write(mensaje.toString()); // Escribir mensaje
                writer.close();
                System.out.println("6 - " + cliente.getReplyString());

                boolean exito = cliente.completePendingCommand();
                System.out.println("7 - " + cliente.getReplyString());

                if (!exito) {
                    System.out.println("Error: Fallo al finalizar la transacción.");
                    System.exit(1);
                } else {
                    System.out.println("Mensaje enviado con éxito.");
                }
            } else {
                System.out.println("Error: Usuario no identificado.");
            }
        } catch (IOException ex) {
            System.err.println("Error: No se pudo conectar al servidor.");
            ex.printStackTrace();
            System.exit(1);
        }

        // Cerrar conexión con el servidor
        try {
            cliente.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Fin del envío.");
        System.exit(0);
    }
}
