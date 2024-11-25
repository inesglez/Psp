package com.example.temaa3.actividad3_7;

import java.io.Serializable;

public class Numeros implements Serializable {
    private int numero;
    private long cuadrado;
    private long cubo;

    // Constructor con parámetros
    public Numeros(int numero) {
        this.numero = numero;
        calcularCuadradoYcubo();
    }

    // Constructor sin parámetros
    public Numeros() {}

    // Métodos get y set
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
        calcularCuadradoYcubo();  // Actualizamos los cálculos si cambiamos el número
    }

    public long getCuadrado() {
        return cuadrado;
    }

    public long getCubo() {
        return cubo;
    }

    // Método para calcular cuadrado y cubo
    private void calcularCuadradoYcubo() {
        this.cuadrado = (long) numero * numero;
        this.cubo = (long) numero * numero * numero;
    }
}
