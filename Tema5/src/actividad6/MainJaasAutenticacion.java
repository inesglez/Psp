package actividad6;
package jaas_autz;

import javax.security.auth.login.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class MainJaasAutenticacion {
    public static void main(String[] args) {
        try {
            LoginContext lc = new LoginContext("EjemploLogin", new MyCallbackHandler());
            lc.login();
            System.out.println("Autenticación exitosa.");

            // Ejecutar la acción autenticada
            EjemploAccion accion = new EjemploAccion();
            AccessController.doPrivileged(accion);

            lc.logout();
            System.out.println("Sesión cerrada.");
        } catch (LoginException e) {
            System.err.println("Error en la autenticación: " + e.getMessage());
        }
    }
}
