package actividad5;

import javax.security.auth.login.*;
import javax.security.auth.callback.*;

public class MainJaasAutenticacion {
    public static void main(String[] args) {
        try {
            // Crea un manejador de callbacks para la autenticación
            CallbackHandler handler = new MyCallbackHandler();

            // Crea un contexto de autenticación con el módulo de JAAS
            LoginContext loginContext = new LoginContext("EjemploJaasLogin", handler);

            // Inicia el proceso de autenticación
            loginContext.login();

            // Obtener las credenciales ingresadas
            NameCallback nameCB = new NameCallback("Nombre de usuario: ");
            PasswordCallback passwordCB = new PasswordCallback("Clave: ", false);
            handler.handle(new Callback[]{nameCB, passwordCB});

            String usuario = nameCB.getName();
            String clave = new String(passwordCB.getPassword());

            // Validación del usuario y la clave
            if ("pedro".equals(usuario) && "abcd".equals(clave)) {
                System.out.println("Usuario autenticado...");
            } else {
                System.out.println("ERROR=> No se puede autenticar el usuario.");
            }
        } catch (LoginException e) {
            System.out.println("ERROR=> No se puede autenticar el usuario.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
