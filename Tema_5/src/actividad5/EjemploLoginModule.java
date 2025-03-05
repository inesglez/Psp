package actividad5;

import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.login.LoginException;
import java.util.Map;

public class EjemploLoginModule implements LoginModule {
    private CallbackHandler callbackHandler;

    public void initialize(Subject subject, CallbackHandler handler, Map<String, ?> state, Map<String, ?> options) {
        this.callbackHandler = handler;
    }

    public boolean login() throws LoginException {
        if (callbackHandler == null) {
            throw new LoginException("❌ Se necesita un CallbackHandler.");
        }

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("👤 Usuario:");
        callbacks[1] = new PasswordCallback("🔑 Clave:", false);

        try {
            // Solicitar los datos al CallbackHandler
            callbackHandler.handle(callbacks);
            String usuario = ((NameCallback) callbacks[0]).getName();
            String clave = new String(((PasswordCallback) callbacks[1]).getPassword());

            // Imprimir usuario y clave ingresados para depuración
            System.out.println("Usuario ingresado: " + usuario);
            System.out.println("Clave ingresada: " + clave);

            // Verificación de usuario y clave
            if ("pedro".equalsIgnoreCase(usuario) && "abcd".equals(clave)) {
                return true;
            } else {
                throw new LoginException("❌ Usuario o clave incorrectos.");
            }
        } catch (Exception e) {
            throw new LoginException("❌ Error en autenticación.");
        }
    }

    public boolean commit() { return true; }

    public boolean abort() { return true; }

    public boolean logout() { return true; }
}
