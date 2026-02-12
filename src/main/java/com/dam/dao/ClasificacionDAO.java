package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.ClasificacionInicial;
import com.dam.model.Temporada;
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

    private EntityManager em = JpaUtil.getEntityManager();

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

    public List<ClasificacionInicial> obtenerClasificacionPorJornada(Temporada temporada, int jornadaNumero) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT c FROM ClasificacionInicial c " +
                                    "WHERE c.temporada = :temporada AND c.jornada.numero = :numero " +
                                    "ORDER BY c.puntos DESC",
                            ClasificacionInicial.class
                    )
                    .setParameter("temporada", temporada)
                    .setParameter("numero", jornadaNumero)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public List<ClasificacionInicial> obtenerTopEquipos(
            Temporada temporada,
            int jornadaNumero,
            boolean ascendente
    ) {

        String orden = ascendente ? "ASC" : "DESC";

        return em.createQuery(
                        "SELECT c FROM ClasificacionInicial c " +
                                "JOIN c.jornada j " +
                                "WHERE c.temporada = :temporada " +
                                "AND j.numero = :numero " +
                                "ORDER BY c.puntos " + orden,
                        ClasificacionInicial.class
                )
                .setParameter("temporada", temporada)
                .setParameter("numero", jornadaNumero)
                .setMaxResults(3)
                .getResultList();
    }


}
