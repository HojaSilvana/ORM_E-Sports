package com.dam.model;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;


import java.util.HashSet;
import java.util.Set;



import java.util.List;

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
    @OneToMany(mappedBy = "equipo")
    private Set<Jugador> jugadores = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "stadium_id")
    private Estadio  estadio;

    @ManyToMany(mappedBy = "equipos")
    private Set<Temporada> temporadas = new HashSet<>();


    @OneToMany(mappedBy = "equipo")
    private Set<Patrocinio> patrocinios;




    public Equipo() {
    }

    public Equipo(String nombre, String region) {
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

    //Metodo para añadir jugador
    public void addJugador(Jugador jugador) {
        jugadores.add(jugador);
        jugador.setEquipo(this);
    }

    public Set<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipo)) return false;
        Equipo equipo = (Equipo) o;
        return id != null && id.equals(equipo.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }



}




