package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Competicion;
import com.dam.model.Equipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Esta clase se encarga del acceso a datos de la entidad Competicion.
 *
 * @author David
 */

public class CompeticionDAO {

    public void save(Competicion competicion) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(competicion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Competicion findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Competicion.class, id);
        } finally {
            em.close();
        }
    }

    public List<Competicion> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Competicion> query =
                    em.createQuery("SELECT c FROM Competicion c", Competicion.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> obtenerCaracteristicasCompeticion() {

        EntityManager em = JpaUtil.getEntityManager();

        try {

            String sql = """
                SELECT name, Creation_Date, TeamsQuantity, MatchQuantity
                FROM competition
                """;

            Query query = em.createNativeQuery(sql);

            return query.getResultList();

        } finally {
            em.close();
        }
    }


}
