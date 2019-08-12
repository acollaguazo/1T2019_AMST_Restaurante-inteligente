package com.example.appgrupo4;

public class Mesa {
    private int imagen;
    private String id;
    private String capacidad;
    private String estado;

    public Mesa(String id, String capacidad, String estado, int imagen) {
        this.imagen = imagen;
        this.id = id;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getImagen() {
        return imagen;
    }

    public String getID() {
        return id;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public String getEstado() {
        return estado;
    }
}