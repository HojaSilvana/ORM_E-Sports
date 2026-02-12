package com.dam.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Representa el patrocinio de un patrocinador a un equipo en una temporada concreta.
 *
 * @author David
 */
@Entity
@Table(name = "sponsorship")
public class Patrocinio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== RELACIÓN CON EQUIPO =====
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Equipo equipo;

    // ===== RELACIÓN CON PATROCINADOR =====
    @ManyToOne
    @JoinColumn(name = "sponsor_id", nullable = false)
    private Patrocinador patrocinador;

    // ===== DATOS ECONÓMICOS =====
    @Column(name = "amount", nullable = false)
    private double cantidad;

    @Column(name = "start_date")
    private LocalDate fechaInicio;

    @Column(name = "end_date")
    private LocalDate fechaFin;


    public Patrocinio() {}

    public Patrocinio(Equipo equipo, Patrocinador patrocinador,
                      double cantidad, LocalDate fechaInicio, LocalDate fechaFin) {
        this.equipo = equipo;
        this.patrocinador = patrocinador;
        this.cantidad = cantidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // ===== GETTERS Y SETTERS =====

    public Long getId() {
        return id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Patrocinador getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
