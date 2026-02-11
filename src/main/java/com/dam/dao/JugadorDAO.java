package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Jugador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JugadorDAO {

    public void save(Jugador jugador) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(jugador);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Jugador> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Jugador> query =
                    em.createQuery("SELECT j FROM Jugador j", Jugador.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
