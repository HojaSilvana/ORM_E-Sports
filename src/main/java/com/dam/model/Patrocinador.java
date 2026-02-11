package com.dam.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa una entidad patrocinadora.
 */
@Entity
@Table(name = "sponsor")
public class Patrocinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String nombre;


    @OneToMany(mappedBy = "patrocinador")
    private Set<Patrocinio> patrocinio = new HashSet<>();


    public Patrocinador() {}

    public Patrocinador(String nombre) {
        this.nombre = nombre;
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

}
