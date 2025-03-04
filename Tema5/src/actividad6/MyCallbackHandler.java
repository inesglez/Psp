package actividad6;

import javax.security.auth.callback.*;
import java.io.IOException;
import java.util.Scanner;

public class MyCallbackHandler implements CallbackHandler {
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        Scanner scanner = new Scanner(System.in);
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                System.out.print(((NameCallback) callback).getPrompt());
                ((NameCallback) callback).setName(scanner.nextLine());
            } else if (callback instanceof PasswordCallback) {
                System.out.print(((PasswordCallback) callback).getPrompt());
                ((PasswordCallback) callback).setPassword(scanner.nextLine().toCharArray());
            } else {
                throw new UnsupportedCallbackException(callback, "Tipo de callback no soportado");
            }
        }
    }
}
