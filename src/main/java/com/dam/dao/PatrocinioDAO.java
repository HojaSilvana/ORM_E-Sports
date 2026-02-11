package com.dam.dao;

import com.dam.config.JpaUtil;
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
}
