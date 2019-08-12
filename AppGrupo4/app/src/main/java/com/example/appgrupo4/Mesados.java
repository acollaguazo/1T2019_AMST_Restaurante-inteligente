package com.example.appgrupo4;

    public class Mesados {
        private String iddos;
        private String capacidaddos;
        private String estadodos;

        public Mesados(String id, String capacidad, String estado) {
            this.iddos = id;
            this.capacidaddos = capacidad;
            this.estadodos = estado;
        }

        public String getIDdos() {
            return iddos;
        }

        public String getCapacidaddos() {
            return capacidaddos;
        }

        public String getEstadodos() {
            return estadodos;
        }
}
