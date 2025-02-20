package org.example.EjercicioTema3;

public class Asignatura {
    String nombreasig;
    int id;

    public Asignatura(String nombreasig, int id) {
        super();
        this.nombreasig = nombreasig;
        this.id = id;
    }
    public Asignatura() { super ();}

    public String getNombreasig() { return nombreasig;}
    public void setNombreasig(String nombreasig) { this.nombreasig = nombreasig;}
    public int getId() { return id;}
    public void setId(int id) { this.id = id;}
}
