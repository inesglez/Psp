package org.example.EjercicioTema3;


class Especialidad {
    int id;
    String nombre;

    public Especialidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
