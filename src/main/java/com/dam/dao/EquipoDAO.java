package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Equipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EquipoDAO {

    public void save(Equipo equipo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(equipo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Equipo findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Equipo.class, id);
        } finally {
            em.close();
        }
    }

    public List<Equipo> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Equipo> query =
                    em.createQuery("SELECT e FROM Equipo e", Equipo.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Equipo equipo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(equipo) ? equipo : em.merge(equipo));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
