package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Partido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PartidoDAO {
    private EntityManager em = JpaUtil.getEntityManager();


    public void save(Partido partido) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(partido);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Partido> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Partido> query =
                    em.createQuery("SELECT p FROM Partido p", Partido.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Partido> obtenerPartidosPorJornada(Long jornadaId) {

        EntityManager em = JpaUtil.getEntityManager();

        List<Partido> partidos = em.createQuery(
                        "SELECT p FROM Partido p WHERE p.jornada.id = :id",
                        Partido.class
                )
                .setParameter("id", jornadaId)
                .getResultList();

        em.close();
        return partidos;
    }

    public List<Partido> findByJornada(Long jornadaId) {
        return em.createQuery(
                        "SELECT p FROM Partido p WHERE p.jornada.id = :id",
                        Partido.class
                )
                .setParameter("id", jornadaId)
                .getResultList();
    }


}
