package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Patrocinador;
import com.dam.model.Patrocinio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PatrocinioDAO {

    public void save(Patrocinio patrocinio) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(patrocinio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Patrocinio> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Patrocinio> query =
                    em.createQuery("SELECT p FROM Patrocinio p", Patrocinio.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Patrocinador> obtenerPatrocinadoresPorEquipo(Long equipoId) {
        EntityManager em = JpaUtil.getEntityManager();

        List<Patrocinador> patrocinadores = em.createQuery(
                        "SELECT p.patrocinador FROM Patrocinio p WHERE p.equipo.id = :id",
                        Patrocinador.class
                ).setParameter("id", equipoId)
                .getResultList();

        em.close();
        return patrocinadores;
    }
    public List<Patrocinio> obtenerTodosLosFichajes() {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT p FROM Patrocinio p " +
                            "ORDER BY p.fechaInicio ASC",
                    Patrocinio.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Patrocinador> obtenerPatrocinadoresComunes(Long equipo1Id, Long equipo2Id) {

        EntityManager em = JpaUtil.getEntityManager();
        List<Patrocinador> resultado;

        try {
            resultado = em.createQuery(
                            """
                            SELECT p1.patrocinador
                            FROM Patrocinio p1
                            WHERE p1.equipo.id = :equipo1
                            AND p1.patrocinador.id IN (
                                SELECT p2.patrocinador.id
                                FROM Patrocinio p2
                                WHERE p2.equipo.id = :equipo2
                            )
                            """,
                            Patrocinador.class
                    )
                    .setParameter("equipo1", equipo1Id)
                    .setParameter("equipo2", equipo2Id)
                    .getResultList();

        } finally {
            em.close();
        }

        return resultado;
    }





}
