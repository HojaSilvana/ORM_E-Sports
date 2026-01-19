package com.dam.model;

import jakarta.persistence.*;

/**
 * Esta clase se encarga de representar un equipo de la competición.
 *
 * @author David Cuenca
 */

@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String nombre;
    @Column(name = "region")
    private String region;

    public Equipo() {
    }

    public Equipo(Long id, String nombre, String region) {
        this.id = id;
        this.nombre = nombre;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}




