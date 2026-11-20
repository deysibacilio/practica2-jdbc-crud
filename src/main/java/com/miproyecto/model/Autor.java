package com.miproyecto.model;

public class Autor {

    private Integer id;
    private String nombre;

    //constructor con parametros
    public Autor(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Autor(String nombre) {
        this(null, nombre);
    }

    //constructor sin parametros
    public Autor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre=" + nombre +
                '}';
    }
}
