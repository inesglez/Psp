package actividad5;

import java.io.*;
import javax.security.auth.callback.*;

public class MyCallbackHandler implements CallbackHandler {
    private String usuario;
    private String clave;

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                NameCallback nameCB = (NameCallback) callback;
                System.out.print(nameCB.getPrompt() + " ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                usuario = br.readLine();
                nameCB.setName(usuario);
            } else if (callback instanceof PasswordCallback) {
                PasswordCallback passwordCB = (PasswordCallback) callback;
                System.out.print(passwordCB.getPrompt() + " ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                clave = br.readLine();
                passwordCB.setPassword(clave.toCharArray());
            }
        }
        // Imprimir usuario y clave leída desde la entrada estándar
        System.out.println("Usuario ingresado en el handler: " + usuario);
        System.out.println("Clave ingresada en el handler: " + clave);
    }
}
