package com.example.tema3_2.Actividad3_8.ejercicio3;

public class Curso {
    private String id;
    private String descripcion;

    public Curso(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Curso{id='" + id + "', descripcion='" + descripcion + "'}";
    }
}
