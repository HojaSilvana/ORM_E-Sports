package com.dam.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta clase representa una temporada asociada a una competición,
 * gestionando equipos, jornadas y clasificación.
 *
 * @author David Cuenca
 */


@Entity
@Table(name = "Season")
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year", nullable = false)
    private int año;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competicion_id", nullable = false)
    private Competicion competicion;


    @OneToMany(
            mappedBy = "temporada",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Jornada> listaJornadas = new HashSet<>();

    @OneToMany(
            mappedBy = "temporada",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<ClasificacionInicial> clasifiacionEquipos = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "temporada_equipo",
            joinColumns = @JoinColumn(name = "temporada_id"),
            inverseJoinColumns = @JoinColumn(name = "equipo_id")
    )
    private Set<Equipo> equipos = new HashSet<>();


    public Temporada() {}

    public Temporada(int año, Competicion competicion) {
        this.año = año;
        this.competicion = competicion;
    }

    // =========================
    // MÉTODOS HELPER IMPORTANTES
    // =========================

    public void addJornada(Jornada jornada) {
        listaJornadas.add(jornada);
        jornada.setTemporada(this);
    }

    public void addEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public void addClasificacion(ClasificacionInicial clasificacion) {
        clasifiacionEquipos.add(clasificacion);
        clasificacion.setTemporada(this);
    }


    public Long getId() {
        return id;
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

    public Set<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(Set<Equipo> equipos) {
        this.equipos = equipos;
    }
}
