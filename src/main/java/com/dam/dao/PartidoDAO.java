package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Partido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PartidoDAO {

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
}
