package com.example.temaa3.actividad3_8.actividad3_7;

import java.io.Serializable;

public class Numeros implements Serializable {
    private int numero;
    private long cuadrado;
    private long cubo;

    // Constructor con parámetros
    public Numeros(int numero) {
        this.numero = numero;
        this.cuadrado = (long) numero * numero;
        this.cubo = (long) numero * numero * numero;
    }

    // Constructor sin parámetros
    public Numeros() {
        this.numero = 0;
        this.cuadrado = 0;
        this.cubo = 0;
    }

    // Métodos getter y setter
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
        this.cuadrado = (long) numero * numero;
        this.cubo = (long) numero * numero * numero;
    }

    public long getCuadrado() {
        return cuadrado;
    }

    public long getCubo() {
        return cubo;
    }

    // Método toString
    @Override
    public String toString() {
        return "Numeros [numero=" + numero + ", cuadrado=" + cuadrado + ", cubo=" + cubo + "]";
    }
}
