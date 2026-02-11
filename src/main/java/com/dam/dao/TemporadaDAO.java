package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Temporada;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TemporadaDAO {

    public void save(Temporada temporada) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(temporada);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Temporada findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Temporada.class, id);
        } finally {
            em.close();
        }
    }

    public List<Temporada> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Temporada> query =
                    em.createQuery("SELECT t FROM Temporada t", Temporada.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
