package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Patrocinador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PatrocinadorDAO {

    public void save(Patrocinador patrocinador) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(patrocinador);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Patrocinador> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Patrocinador> query =
                    em.createQuery("SELECT p FROM Patrocinador p", Patrocinador.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Patrocinador> obtenerPatrocinadoresDeEquipo(Long equipoId) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery("""
            SELECT p.patrocinador
            FROM Patrocinio p
            WHERE p.equipo.id = :id
        """, Patrocinador.class)
                    .setParameter("id", equipoId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

}
