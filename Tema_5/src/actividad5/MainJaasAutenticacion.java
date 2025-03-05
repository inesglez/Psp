package actividad5;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.*;

public class MainJaasAutenticacion {
    public static void main(String[] args) {
        // Solicitar usuario y contraseña desde entrada estándar
        CallbackHandler handler = new MyCallbackHandler();

        try {
            LoginContext loginContext = new LoginContext("EjemploLogin", handler);
            loginContext.login();
            System.out.println("✅ Usuario autenticado correctamente.");
        } catch (LoginException e) {
            System.err.println("❌ ERROR: No se puede autenticar el usuario.");
        }
    }
}
