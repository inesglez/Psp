package Ejer_2;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FTPLogger {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int successfulConnections = 0;

        while (true) {
            // Solicitar nombre de usuario para el login
            System.out.print("Introduce el nombre de usuario (o * para salir): ");
            String username = scanner.nextLine();

            // Si el usuario ingresa '*' se termina el programa
            if (username.equals("*")) {
                break;
            }

            // Solicitar contraseña para el login
            System.out.print("Introduce la contraseña: ");
            String password = scanner.nextLine();

            FTPClient ftpClient = new FTPClient();
            try {
                // Conectar al servidor FTP
                ftpClient.connect("localhost");
                // Intentar hacer login con el nombre de usuario y contraseña proporcionados
                boolean login = ftpClient.login(username, password);

                if (login) {
                    // Si el login es exitoso, se incrementa el contador de conexiones exitosas
                    System.out.println("Login correcto...");
                    successfulConnections++;

                    // Establecer el directorio de logs del usuario
                    String logDirectory = "/" + username + "/LOG";
                    // Cambiar al directorio LOG
                    if (!ftpClient.changeWorkingDirectory(logDirectory)) {
                        System.out.println("Error: No se pudo acceder al directorio LOG.");
                        continue;
                    }

                    // Nombre del archivo de log
                    String logFile = "LOG.TXT";
                    InputStream inputStream = ftpClient.retrieveFileStream(logFile);
                    StringBuilder logContent = new StringBuilder();
                    if (inputStream != null) {
                        // Leer el contenido del archivo de log si existe
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            logContent.append(line).append("\n");
                        }
                        reader.close();
                        inputStream.close();
                    } else {
                        // Si no existe, se genera un log básico
                        logContent.append("Conexiones del usuario.\n");
                    }

                    // Obtener la fecha y hora actual
                    String timestamp = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").format(new Date());
                    // Añadir la hora de conexión al log
                    logContent.append("Hora de conexión: ").append(timestamp).append("\n");

                    // Convertir el contenido del log a un flujo de bytes y almacenarlo de nuevo en el archivo
                    ByteArrayInputStream newLogContent = new ByteArrayInputStream(logContent.toString().getBytes());
                    ftpClient.storeFile(logFile, newLogContent);
                    newLogContent.close();

                    // Cerrar sesión del FTP
                    ftpClient.logout();
                } else {
                    // Si el login falla
                    System.out.println("Login Incorrecto...");
                }
            } catch (IOException ex) {
                // Manejo de errores si ocurre algún problema con la conexión FTP
                ex.printStackTrace();
            } finally {
                try {
                    // Desconectar el cliente FTP, si fue conectado
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    // Manejo de errores si ocurre algún problema al desconectar
                    ex.printStackTrace();
                }
            }
        }

        // Después de procesar todas las conexiones, enviar un correo con el conteo de conexiones exitosas
        sendEmail(successfulConnections);
    }

    private static void sendEmail(int successfulConnections) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar detalles para enviar un correo SMTP
        System.out.print("Introduce servidor SMTP: ");
        String server = scanner.nextLine();

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

        // Definir el asunto y el mensaje del correo
        String asunto = "Conexiones FTP exitosas";
        String mensaje = "Número de conexiones exitosas: " + successfulConnections;

        AuthenticatingSMTPClient cliente = new AuthenticatingSMTPClient();

        try {
            // Conectar al servidor SMTP
            cliente.connect(server, puerto);
            cliente.ehlo(server);

            // Intentar autenticar al cliente SMTP
            if (cliente.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN, userName, password)) {
                // Crear la cabecera del correo
                SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destinatario, asunto);
                cliente.setSender(remitente);
                cliente.addRecipient(destinatario);

                // Enviar el mensaje
                Writer writer = cliente.sendMessageData();
                if (writer != null) {
                    writer.write(cabecera.toString());
                    writer.write(mensaje);
                    writer.close();
                }

                // Completar el envío del mensaje
                cliente.completePendingCommand();
                System.out.println("Correo enviado con éxito.");
            } else {
                System.out.println("Error: Usuario no identificado.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                cliente.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
