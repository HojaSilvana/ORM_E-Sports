package com.dam.model;

import jakarta.persistence.*;

@Entity
@Table (name = "Classification")

public class ClasificacionInicial {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "points", nullable = false)
    private int puntos;
    @JoinColumn(name = "matchesplayed", nullable = false)
    private int partidosJugados;
    @JoinColumn(name = "wins", nullable = false)
    private int victorias;
    @JoinColumn(name = "losses", nullable = false)
    private int derrotas;

    @ManyToOne
    @JoinColumn (name = "session_id", nullable = false)
    private Temporada temporada;

    @ManyToOne
    @JoinColumn (name = "team_id", nullable = false)
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "jornada_id")
    private Jornada jornada;


    public ClasificacionInicial() {
    }

    public ClasificacionInicial(int puntos, int partidosJugados, int victorias, int derrotas, Temporada temporada, Equipo equipo, Jornada jornada) {
        this.puntos = puntos;
        this.partidosJugados = partidosJugados;
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.temporada = temporada;
        this.equipo = equipo;
        this.jornada = jornada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
}