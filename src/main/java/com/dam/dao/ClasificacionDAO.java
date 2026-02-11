package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.ClasificacionInicial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Esta clase se encarga de gestionar el acceso a datos
 * de la entidad ClasificacionInicial.
 *
 * @author David Cuenca
 */
public class ClasificacionDAO {

    public void save(ClasificacionInicial clasificacion) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(clasificacion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<ClasificacionInicial> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<ClasificacionInicial> query =
                    em.createQuery("SELECT c FROM ClasificacionInicial c ORDER BY c.puntos DESC",
                            ClasificacionInicial.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
