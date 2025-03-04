<<<<<<< HEAD
package actividad4_6;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ejercicio6 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username, password;

        while (true) {
            System.out.print("Ingrese el nombre de usuario (* para salir): ");
            username = scanner.nextLine();

            if (username.equals("*")) {
                System.out.println("Saliendo del sistema...");
                break;
            }

            System.out.print("Ingrese la contraseña: ");
            password = scanner.nextLine();

            if (autentificaciónTFP(username, password)) {
                System.out.println("Conexión exitosa.");
                logConnection(username, password);
            } else {
                System.out.println("Credenciales incorrectas. Se enviará un correo electrónico de alerta.");
                sendAlertEmail(username);
            }
        }

        scanner.close();
    }

    //Verificamos las credenciales directamente en el servidor FTP
    private static boolean autentificaciónTFP (String username, String password) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("localhost");
            boolean isLoggedIn = ftpClient.login(username, password);
            ftpClient.logout();
            ftpClient.disconnect();
            return isLoggedIn;
        } catch (IOException e) {
            System.err.println("Error al autenticar en el servidor FTP: " + e.getMessage());
            return false;
        }
    }

    //Registramos la conexión del usuario en su archivo LOG.TXT correspondiente en el servidor FTP
    private static void logConnection(String username, String password) {
        FTPClient ftpClient = new FTPClient();

        try {
            //Nos conectamos al servidor FTP
            ftpClient.connect("localhost");
            if (ftpClient.login(username, password)) {
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                //Creamos la carpeta LOG en caso de que no exista
                String logDir = "/" + username + "/LOG";
                if (!ftpClient.changeWorkingDirectory(logDir)) {
                    ftpClient.makeDirectory(logDir);
                    ftpClient.changeWorkingDirectory(logDir);
                }

                //Preparamos el contenido para LOG.TXT
                String logFileName = "LOG.TXT";
                String timestamp = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).format(new Date());
                String logContent = "Conexiones del usuario:\nHora de conexión: " + timestamp + "\n\n";

                //Subir/actualizar el archivo LOG.TXT
                InputStream inputStream = new ByteArrayInputStream(logContent.getBytes());
                ftpClient.storeFile(logFileName, inputStream);
                inputStream.close();

                System.out.println("Conexión registrada en " + logDir + "/" + logFileName);
            } else {
                System.out.println("No se pudo autenticar en el servidor FTP.");
            }

            ftpClient.logout();
        } catch (IOException e) {
            System.err.println("Error al registrar la conexión: " + e.getMessage());
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException ex) {
                System.err.println("Error al desconectar del servidor FTP: " + ex.getMessage());
            }
        }
    }

    //Simulamos el envío de un correo electrónico de alerta (no me ha quedado claro a donde hacerlo)
    private static void sendAlertEmail(String username) {
        System.out.println("[SIMULACIÓN] Correo electrónico enviado al administrador: \"Usuario " + username + " intentó conectarse con credenciales incorrectas.\"");
    }
=======
package actividad4_6;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ejercicio6 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username, password;

        while (true) {
            System.out.print("Ingrese el nombre de usuario (* para salir): ");
            username = scanner.nextLine();

            if (username.equals("*")) {
                System.out.println("Saliendo del sistema...");
                break;
            }

            System.out.print("Ingrese la contraseña: ");
            password = scanner.nextLine();

            if (autentificaciónTFP(username, password)) {
                System.out.println("Conexión exitosa.");
                logConnection(username, password);
            } else {
                System.out.println("Credenciales incorrectas. Se enviará un correo electrónico de alerta.");
                sendAlertEmail(username);
            }
        }

        scanner.close();
    }

    //Verificamos las credenciales directamente en el servidor FTP
    private static boolean autentificaciónTFP (String username, String password) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("localhost");
            boolean isLoggedIn = ftpClient.login(username, password);
            ftpClient.logout();
            ftpClient.disconnect();
            return isLoggedIn;
        } catch (IOException e) {
            System.err.println("Error al autenticar en el servidor FTP: " + e.getMessage());
            return false;
        }
    }

    //Registramos la conexión del usuario en su archivo LOG.TXT correspondiente en el servidor FTP
    private static void logConnection(String username, String password) {
        FTPClient ftpClient = new FTPClient();

        try {
            //Nos conectamos al servidor FTP
            ftpClient.connect("localhost");
            if (ftpClient.login(username, password)) {
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                //Creamos la carpeta LOG en caso de que no exista
                String logDir = "/" + username + "/LOG";
                if (!ftpClient.changeWorkingDirectory(logDir)) {
                    ftpClient.makeDirectory(logDir);
                    ftpClient.changeWorkingDirectory(logDir);
                }

                //Preparamos el contenido para LOG.TXT
                String logFileName = "LOG.TXT";
                String timestamp = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).format(new Date());
                String logContent = "Conexiones del usuario:\nHora de conexión: " + timestamp + "\n\n";

                //Subir/actualizar el archivo LOG.TXT
                InputStream inputStream = new ByteArrayInputStream(logContent.getBytes());
                ftpClient.storeFile(logFileName, inputStream);
                inputStream.close();

                System.out.println("Conexión registrada en " + logDir + "/" + logFileName);
            } else {
                System.out.println("No se pudo autenticar en el servidor FTP.");
            }

            ftpClient.logout();
        } catch (IOException e) {
            System.err.println("Error al registrar la conexión: " + e.getMessage());
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException ex) {
                System.err.println("Error al desconectar del servidor FTP: " + ex.getMessage());
            }
        }
    }

    //Simulamos el envío de un correo electrónico de alerta (no me ha quedado claro a donde hacerlo)
    private static void sendAlertEmail(String username) {
        System.out.println("[SIMULACIÓN] Correo electrónico enviado al administrador: \"Usuario " + username + " intentó conectarse con credenciales incorrectas.\"");
    }
>>>>>>> 444a3dd9ac82ce51985fadaae17bd687d3663045
}