package com.dam.model;

import jakarta.persistence.*;

@Entity
@Table(name = "match")
public class Partido {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "matchday_id", nullable = false)
    private Jornada jornada;

    @ManyToOne
    @JoinColumn(name = "homeTeam_id", nullable = false)
    private Equipo equipoLocal;

    @ManyToOne
    @JoinColumn(name = "AwayTeam_id", nullable = false)
    private Equipo equipoVisitante;

    private int scoreEquipoLocal;
    private int scoreEquipoVisitante;

    public Partido(Jornada jornada, Equipo equipoLocal, Equipo equipoVisitante) {
        this.jornada = jornada;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
    }

    public Partido() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public int getScoreEquipoLocal() {
        return scoreEquipoLocal;
    }

    public void setScoreEquipoLocal(int scoreEquipoLocal) {
        this.scoreEquipoLocal = scoreEquipoLocal;
    }

    public int getScoreEquipoVisitante() {
        return scoreEquipoVisitante;
    }

    public void setScoreEquipoVisitante(int scoreEquipoVisitante) {
        this.scoreEquipoVisitante = scoreEquipoVisitante;
    }
}
