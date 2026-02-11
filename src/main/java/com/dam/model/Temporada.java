package com.dam.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table (name = "Season")
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year", nullable = false)
    private int año;

    @ManyToOne
    @JoinColumn (name = "competicion_id", nullable = false)
    private Competicion competicion;

    @OneToMany(mappedBy = "temporada")
    private Set<Jornada> listaJornadas = new HashSet<>();

    //Esto es asi porque los equipos tienen varias clasificaciones (una en 2025, otra en 2026,etc)
    @OneToMany (mappedBy = "temporada")
    private Set<ClasificacionInicial> clasifiacionEquipos = new HashSet<>();

    public Temporada(int año, Competicion competicion) {
        this.año = año;
        this.competicion = competicion;
    }

    public Temporada() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    public Set<Jornada> getListaJornadas() {
        return listaJornadas;
    }

    public void setListaJornadas(Set<Jornada> listaJornadas) {
        this.listaJornadas = listaJornadas;
    }

    public Set<ClasificacionInicial> getClasifiacionEquipos() {
        return clasifiacionEquipos;
    }

    public void setClasifiacionEquipos(Set<ClasificacionInicial> clasifiacionEquipos) {
        this.clasifiacionEquipos = clasifiacionEquipos;
    }
}
