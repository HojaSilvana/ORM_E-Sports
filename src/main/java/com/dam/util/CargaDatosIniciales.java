package com.dam.util;

import com.dam.config.JpaUtil;
import com.dam.model.*;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public class CargaDatosIniciales {

    public static void cargar() {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // ===== COMPETICIÓN =====
            Competicion competicion = new Competicion(
                    "League of Legends World Cup",
                    LocalDate.now(),
                    3,
                    8
            );

            em.persist(competicion);


            // ===== EQUIPOS =====
            Equipo g2 = crearEquipo(em, "G2 Esports", "LEC", "G2 Arena", "Berlin");
            Equipo t1 = crearEquipo(em, "T1", "LCK", "T1 Base", "Seoul");
            Equipo mkoi = crearEquipo(em, "Movistar KOI", "LEC", "KOI Center", "Madrid");
            Equipo fnatic = crearEquipo(em, "Fnatic", "LEC", "Fnatic HQ", "London");
            Equipo geng = crearEquipo(em, "Gen.G", "LCK", "GenG Dome", "Seoul");
            Equipo blg = crearEquipo(em, "Bilibili Gaming", "LPL", "BLG Arena", "Shanghai");
            Equipo jdg = crearEquipo(em, "JD Gaming", "LPL", "JDG Center", "Beijing");
            Equipo lng = crearEquipo(em, "LNG Esports", "LPL", "LNG Stadium", "Suzhou");

            // ===== JUGADORES =====
            crearJugador(em, "Caps", "Denmark", LocalDate.of(1999, 11, 17), g2);
            crearJugador(em, "BrokenBlade", "Germany", LocalDate.of(2000, 1, 19), g2);
            crearJugador(em, "Yike", "Sweden", LocalDate.of(2002, 6, 11), g2);
            crearJugador(em, "Hans Sama", "France", LocalDate.of(1999, 9, 2), g2);
            crearJugador(em, "Mikyx", "Slovenia", LocalDate.of(1998, 11, 2), g2);

            crearJugador(em, "Faker", "Korea", LocalDate.of(1996, 5, 7), t1);
            crearJugador(em, "Zeus", "Korea", LocalDate.of(2004, 1, 31), t1);
            crearJugador(em, "Oner", "Korea", LocalDate.of(2002, 12, 24), t1);
            crearJugador(em, "Gumayusi", "Korea", LocalDate.of(2002, 2, 6), t1);
            crearJugador(em, "Keria", "Korea", LocalDate.of(2002, 10, 14), t1);

            crearJugador(em, "Elyoya", "Spain", LocalDate.of(2000, 2, 13), mkoi);
            crearJugador(em, "Supa", "Spain", LocalDate.of(2001, 6, 20), mkoi);
            crearJugador(em, "Fresskowy", "Spain", LocalDate.of(2002, 3, 8), mkoi);
            crearJugador(em, "Alvaro", "Spain", LocalDate.of(2001, 11, 4), mkoi);
            crearJugador(em, "Myrwn", "Spain", LocalDate.of(2003, 7, 15), mkoi);

            // ===== PATROCINADORES =====
            Patrocinador nike = new Patrocinador("Nike");
            Patrocinador redbull = new Patrocinador("RedBull");
            Patrocinador intel = new Patrocinador("Intel");

            em.persist(nike);
            em.persist(redbull);
            em.persist(intel);

            // ===== PATROCINIOS =====
            // ===== PATROCINIOS =====
            Patrocinio p1 = new Patrocinio(
                    g2,
                    nike,
                    500000.0,
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 12, 31)
            );

            Patrocinio p2 = new Patrocinio(
                    g2,
                    redbull,
                    300000.0,
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 12, 31)
            );

            Patrocinio p3 = new Patrocinio(
                    t1,
                    intel,
                    800000.0,
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 12, 31)
            );

            em.persist(p1);
            em.persist(p2);
            em.persist(p3);



            em.getTransaction().commit();
            System.out.println("Simulación inicial completada correctamente");

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // HELPERS PARA CREAR EQUIPOS

    private static Equipo crearEquipo(EntityManager em, String nombre, String region,
                                      String nombreEstadio, String ciudad) {

        Estadio estadio = new Estadio(nombreEstadio, ciudad);

        Equipo equipo = new Equipo(nombre, region);
        equipo.setEstadio(estadio);
        estadio.setEquipo(equipo); // solo si la relación es bidireccional

        em.persist(estadio);
        em.persist(equipo);

        return equipo;
    }

    private static void crearJugador(EntityManager em, String nombre, String nacionalidad,
                                     LocalDate fechaNacimiento, Equipo equipo) {

        Jugador jugador = new Jugador(nombre, nacionalidad, fechaNacimiento, equipo);
        em.persist(jugador);
    }
}
