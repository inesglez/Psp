package comprueba_4;

public class Especialidad {
    private int id;
    private String nombreespe;

    public Especialidad(int id, String nombreespe) {
        this.id = id;
        this.nombreespe = nombreespe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreespe() {
        return nombreespe;
    }

    public void setNombreespe(String nombreespe) {
        this.nombreespe = nombreespe;
    }
}
