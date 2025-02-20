package actividad4_4;

import java.util.Scanner;
import org.apache.commons.net.smtp.*;

public class ejercicio4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar datos de configuración del servidor SMTP
        System.out.print("Servidor SMTP (ejemplo: localhost para Mercury): ");
        String servidorSMTP = scanner.nextLine();

        System.out.print("Puerto del servidor (por defecto 25 para Mercury): ");
        int puerto = Integer.parseInt(scanner.nextLine());

        // Solicitar credenciales del usuario
        System.out.print("Nombre de usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String clave = scanner.nextLine();

        // Solicitar datos del correo
        System.out.print("Correo del remitente: ");
        String remitente = scanner.nextLine();

        System.out.print("Correo del destinatario: ");
        String destinatario = scanner.nextLine();

        System.out.print("Asunto del correo: ");
        String asunto = scanner.nextLine();

        // Capturar el mensaje hasta que el usuario ingrese '*'
        System.out.println("Escribe el mensaje y finaliza con '*':");
        StringBuilder cuerpoMensaje = new StringBuilder();
        while (true) {
            String linea = scanner.nextLine();
            if ("*".equals(linea)) break;
            cuerpoMensaje.append(linea).append("\n");
        }
        scanner.close();

        try {
            // Establecer conexión con el servidor SMTP
            AuthenticatingSMTPClient clienteSMTP = new AuthenticatingSMTPClient();
            clienteSMTP.connect(servidorSMTP, puerto);
            System.out.println("Conexión establecida: " + clienteSMTP.getReplyString());

            if (!SMTPReply.isPositiveCompletion(clienteSMTP.getReplyCode())) {
                clienteSMTP.disconnect();
                System.err.println("No se pudo conectar al servidor SMTP.");
                return;
            }

            // Enviar saludo EHLO al servidor
            clienteSMTP.ehlo(servidorSMTP);
            System.out.println("Saludo EHLO enviado: " + clienteSMTP.getReplyString());

            // Autenticación del usuario en el servidor SMTP
            if (clienteSMTP.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, usuario, clave)) {
                System.out.println("Autenticación exitosa: " + clienteSMTP.getReplyString());
            } else {
                System.err.println("Error en la autenticación.");
                return;
            }

            // Configurar y enviar el correo
            SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destinatario, asunto);
            clienteSMTP.setSender(remitente);
            clienteSMTP.addRecipient(destinatario);
            System.out.println("Destinatario agregado: " + clienteSMTP.getReplyString());

            clienteSMTP.sendShortMessageData(cuerpoMensaje.toString());
            System.out.println("Correo enviado correctamente.");

            // Cerrar sesión y desconectar
            clienteSMTP.logout();
            clienteSMTP.disconnect();
            System.out.println("Desconectado del servidor SMTP.");
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
