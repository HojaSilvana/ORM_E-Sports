package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Jornada;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JornadaDAO {

    public void save(Jornada jornada) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(jornada);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Jornada> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Jornada> query =
                    em.createQuery("SELECT j FROM Jornada j", Jornada.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
