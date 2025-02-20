package org.example.EjercicioTema3;
import java.io.*;
import java.net.*;
import java.util.*;

class Profesor {
    int id;
    String nombre;
    String[] asignaturas;
    Especialidad especialidad;

    public Profesor(int id, String nombre, String[] asignaturas, Especialidad especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.asignaturas = asignaturas;
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nNombre: " + nombre + "\nEspecialidad: " + especialidad + "\nAsignaturas: " + String.join(", ", asignaturas);
    }
}
