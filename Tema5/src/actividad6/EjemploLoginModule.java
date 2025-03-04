package actividad6;

import javax.security.auth.spi.LoginModule;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import java.io.IOException;
import java.util.Map;

public class EjemploLoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private boolean autenticado = false;
    private EjemploPrincipal principal;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws FailedLoginException {
        if (callbackHandler == null) {
            throw new FailedLoginException("Falta el manejador de callbacks");
        }

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Usuario: ");
        callbacks[1] = new PasswordCallback("Contraseña: ", false);

        try {
            callbackHandler.handle(callbacks);
            String usuario = ((NameCallback) callbacks[0]).getName();
            String clave = new String(((PasswordCallback) callbacks[1]).getPassword());

            if (usuario.equals("maria") && clave.equals("1234")) {
                autenticado = true;
                principal = new EjemploPrincipal("maria");
            } else if (usuario.equals("juan") && clave.equals("5678")) {
                autenticado = true;
                principal = new EjemploPrincipal("juan");
            } else if (usuario.equals("pedro") && clave.equals("abcd")) { // NUEVO USUARIO
                autenticado = true;
                principal = new EjemploPrincipal("pedro");
            } else {
                throw new FailedLoginException("Usuario o contraseña incorrectos");
            }
        } catch (IOException | UnsupportedCallbackException e) {
            throw new FailedLoginException("Error en la autenticación");
        }
        return autenticado;
    }

    @Override
    public boolean commit() {
        if (!autenticado) {
            return false;
        }
        subject.getPrincipals().add(principal);
        return true;
    }

    @Override
    public boolean abort() {
        autenticado = false;
        principal = null;
        return true;
    }

    @Override
    public boolean logout() {
        subject.getPrincipals().remove(principal);
        autenticado = false;
        principal = null;
        return true;
    }
}
