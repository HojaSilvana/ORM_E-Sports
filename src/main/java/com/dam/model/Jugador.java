package com.dam.model;

import jakarta.persistence.*;


import java.time.LocalDate;

/**
 * Esta clase se encarga de representar un deportista de la competición a lo largo de la app de ORM.
 * @author David
 */

@Entity
@Table(name = "Player")
@NamedQuery(
        name = "Jugador.nuevasIncorporaciones",
        query = "SELECT j FROM Jugador j ORDER BY j.id DESC"
)
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String nombre;
    @Column(name = "nationality")
    private String nacionalidad;
    @Column(name = "birth_date")
    private LocalDate fechaNacimiento;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Equipo equipo;




    public Jugador() {
    }

    public Jugador(String nombre, String nacionalidad, LocalDate fechaNacimiento, Equipo equipo) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.equipo = equipo;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    // Metodo para devolver equipos
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }



}
