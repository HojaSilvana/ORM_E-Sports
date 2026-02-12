package com.dam.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "competition")
public class Competicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "name", nullable = false, unique = true)
    private String nombre;
    @Column(name = "Creation_Date", nullable = false)
    private LocalDate fechaCreacion;
    @Column(name = "MatchQuantity", nullable = false)
    private int cantidadJornadas;
    @Column(name = "TeamsQuantity", nullable = false)
    private int cantidadEquipos;

    @OneToMany(mappedBy = "competicion")
    private Set<Temporada> temporadas = new HashSet<>();


    public Competicion() {
    }

    public Competicion(String nombre, LocalDate fechaCreacion, int cantidadJornadas, int cantidadEquipos) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.cantidadJornadas = cantidadJornadas;
        this.cantidadEquipos = cantidadEquipos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getCantidadJornadas() {
        return cantidadJornadas;
    }

    public void setCantidadJornadas(int cantidadJornadas) {
        this.cantidadJornadas = cantidadJornadas;
    }

    public int getCantidadEquipos() {
        return cantidadEquipos;
    }

    public void setCantidadEquipos(int cantidadEquipos) {
        this.cantidadEquipos = cantidadEquipos;
    }

    public Long getId() {
        return id;
    }

}
