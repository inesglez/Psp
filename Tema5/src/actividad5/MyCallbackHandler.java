package actividad5;

import java.io.*;
import javax.security.auth.callback.*;

public class MyCallbackHandler implements CallbackHandler {
    private String usuario;
    private char[] clave;

    public void handle(Callback[] callbacks) throws IOException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback nameCB = (NameCallback) callbacks[i];

                // Muestra el prompt
                System.out.print(nameCB.getPrompt());

                // Entrada estándar, solicitar el nombre del usuario
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                usuario = br.readLine();  // Lectura por teclado
                nameCB.setName(usuario);
            } else if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback passwordCB = (PasswordCallback) callbacks[i];

                // Muestra el prompt
                System.out.print(passwordCB.getPrompt());

                // Entrada estándar, solicitar la clave del usuario
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                clave = br.readLine().toCharArray();  // Lectura por teclado
                passwordCB.setPassword(clave);
            }
        }
    }
}
