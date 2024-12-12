package com.example.tema3_2.Actividad3_8.ejercicio3;

public class Alumno {
    private String idAlumno;  // Identificador único del alumno
    private String nombre;    // Nombre del alumno
    private String curso;     // Curso en el que está matriculado el alumno
    private int nota;         // Nota obtenida por el alumno

    // Constructor que inicializa los atributos del objeto Alumno
    public Alumno(String idAlumno, String nombre, String curso, int nota) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.curso = curso;
        this.nota = nota;
    }

    // Métodos getter para acceder a los atributos
    public String getIdAlumno() {
        return idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCurso() {
        return curso;
    }

    public int getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return "Alumno{idAlumno='" + idAlumno + "', nombre='" + nombre + "', curso='" + curso + "', nota=" + nota + "}";
    }
}
