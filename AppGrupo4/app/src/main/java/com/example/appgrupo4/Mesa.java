package com.example.appgrupo4;

class Mesa {
    private final int imagen;
    private final String id;
    private final String capacidad;
    private final String estado;

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