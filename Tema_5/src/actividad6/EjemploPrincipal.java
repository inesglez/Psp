package actividad6;

import java.security.Principal;

public class EjemploPrincipal implements Principal {
    private String nombre;

    public EjemploPrincipal(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getName() {
        return nombre;
    }
}
