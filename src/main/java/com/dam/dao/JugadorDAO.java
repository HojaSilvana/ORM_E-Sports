package com.dam.dao;

import com.dam.config.JpaUtil;
import com.dam.model.Jugador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {
    private EntityManager em = JpaUtil.getEntityManager();


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

    public List<Jugador> obtenerJugadoresPorEquipo(Long equipoId) {

        EntityManager em = JpaUtil.getEntityManager();
        List<Jugador> resultado;

        try {
            String jpql = "SELECT j FROM Jugador j WHERE j.equipo.id = :id";

            resultado = em.createQuery(jpql, Jugador.class)
                    .setParameter("id", equipoId)
                    .getResultList();

        } finally {
            em.close();
        }

        return resultado;
    }

    public Double obtenerEdadMediaPorEquipo(Long equipoId) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT AVG(FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', j.fechaNacimiento)) " +
                                    "FROM Jugador j WHERE j.equipo.id = :id",
                            Double.class
                    )
                    .setParameter("id", equipoId)
                    .getSingleResult();

        } finally {
            em.close();
        }
    }

    public List<Object[]> contarJugadoresMayoresDe23PorNacionalidad() {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT j.nacionalidad, COUNT(j) " +
                            "FROM Jugador j " +
                            "WHERE (FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', j.fechaNacimiento)) > 23 " +
                            "GROUP BY j.nacionalidad",
                    Object[].class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public List<Jugador> obtenerNuevasIncorporaciones() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT j FROM Jugador j ORDER BY j.id DESC",
                            Jugador.class
                    )
                    .setMaxResults(3)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Long contarJugadoresPorCompeticion(Long competicionId) {

        EntityManager em = JpaUtil.getEntityManager();
        Long total;

        try {
            total = em.createQuery(
                            "SELECT COUNT(j) " +
                                    "FROM Jugador j " +
                                    "JOIN j.equipo e " +
                                    "JOIN e.temporadas t " +
                                    "JOIN t.competicion c " +
                                    "WHERE c.id = :id",
                            Long.class
                    )
                    .setParameter("id", competicionId)
                    .getSingleResult();

        } finally {
            em.close();
        }

        return total;
    }

    public List<Jugador> filtrarJugadoresCriteria(
            String nombre,
            String nacionalidad,
            Integer edadMin,
            Integer edadMax,
            Long equipoId,
            boolean ordenarPorNombreAsc) {

        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Jugador> cq = cb.createQuery(Jugador.class);

        Root<Jugador> jugador = cq.from(Jugador.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filtro por nombre
        if (nombre != null && !nombre.isEmpty()) {
            predicates.add(cb.like(cb.lower(jugador.get("nombre")),
                    "%" + nombre.toLowerCase() + "%"));
        }

        // Filtro por nacionalidad
        if (nacionalidad != null && !nacionalidad.isEmpty()) {
            predicates.add(cb.equal(jugador.get("nacionalidad"), nacionalidad));
        }

        // Filtro por edad
        if (edadMin != null || edadMax != null) {

            Expression<Integer> edad =
                    cb.diff(
                            cb.function("year", Integer.class, cb.currentDate()),
                            cb.function("year", Integer.class, jugador.get("fechaNacimiento"))
                    );

            if (edadMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(edad, edadMin));
            }
            if (edadMax != null) {
                predicates.add(cb.lessThanOrEqualTo(edad, edadMax));
            }
        }

        // Filtro por equipo
        if (equipoId != null) {
            predicates.add(cb.equal(jugador.get("equipo").get("id"), equipoId));
        }

        cq.select(jugador).where(cb.and(predicates.toArray(new Predicate[0])));

        // Orden
        if (ordenarPorNombreAsc) {
            cq.orderBy(cb.asc(jugador.get("nombre")));
        } else {
            cq.orderBy(cb.desc(jugador.get("nombre")));
        }

        return em.createQuery(cq).getResultList();
    }





}
