package com.dam.model;


import jakarta.persistence.*;

import java.util.*;

@Entity
@Table (name = "MatchDay")
public class Jornada {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "number", nullable = false)
    private int numero;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Temporada temporada;

    @OneToMany (mappedBy = "jornada")
    private Set<Partido> partidos = new HashSet<>();

    public Jornada() {
    }

    public Jornada(int numero, Temporada temporada) {
        this.numero = numero;
        this.temporada = temporada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public Set<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(Set<Partido> partidos) {
        this.partidos = partidos;
    }


}
