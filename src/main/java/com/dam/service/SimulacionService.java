package com.dam.service;

import com.dam.dao.*;
import com.dam.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimulacionService {

    private CompeticionDAO competicionDAO = new CompeticionDAO();
    private TemporadaDAO temporadaDAO = new TemporadaDAO();
    private EquipoDAO equipoDAO = new EquipoDAO();
    private PartidoDAO partidoDAO = new PartidoDAO();
    private JornadaDAO jornadaDAO = new JornadaDAO();
    private JugadorDAO jugadorDAO = new JugadorDAO();
    private ClasificacionService clasificacionService = new ClasificacionService();

    public void iniciarSimulacion() {

        // =========================================================
        // CREACIÓN DE DATOS
        // =========================================================

        List<Competicion> competiciones = competicionDAO.findAll();

        if (competiciones.isEmpty()) {
            throw new RuntimeException("No hay competiciones cargadas. Ejecuta CargaDatosIniciales primero.");
        }

        Competicion competicion = competiciones.get(0);


        Temporada temporada = new Temporada(2026, competicion);
        temporadaDAO.save(temporada);

        System.out.println("Competición y temporada creadas");

        List<Equipo> equipos = equipoDAO.findAll();
        for (Equipo e : equipos) {
            temporada.getEquipos().add(e);

        }

        temporadaDAO.update(temporada);

        System.out.println("Equipos asociados");

        List<Jornada> jornadas = new ArrayList<>();
        for (int i = 1; i <= competicion.getCantidadJornadas(); i++) {
            Jornada jornada = new Jornada(i, temporada);
            jornadaDAO.save(jornada);
            jornadas.add(jornada);
        }

        System.out.println("Jornadas creadas");

        for (Jornada jornada : jornadas) {
            for (int i = 0; i < equipos.size(); i += 2) {

                if (i + 1 < equipos.size()) {
                    Equipo local = equipos.get(i);
                    Equipo visitante = equipos.get(i + 1);

                    Partido partido = new Partido(jornada, local, visitante);
                    partido.setScoreEquipoLocal((int) (Math.random() * 5));
                    partido.setScoreEquipoVisitante((int) (Math.random() * 5));

                    partidoDAO.save(partido);
                }
            }
        }

        System.out.println("Partidos generados");

        for (Jornada j : jornadas) {
            clasificacionService.calcularClasificacion(temporada, j);
        }

        // ===============================
        // GENERAR FICHAJES (PATROCINIOS)
        // ===============================
        System.out.println("Generando fichajes...");

        PatrocinadorDAO patrocinadorDAO = new PatrocinadorDAO();
        PatrocinioDAO patrocinioDAO = new PatrocinioDAO();

        List<Patrocinador> patrocinadores = patrocinadorDAO.findAll();

        if (patrocinadores.isEmpty()) {

            patrocinadorDAO.save(new Patrocinador("Nike"));
            patrocinadorDAO.save(new Patrocinador("Red Bull"));
            patrocinadorDAO.save(new Patrocinador("Adidas"));
            patrocinadorDAO.save(new Patrocinador("Intel"));

            patrocinadores = patrocinadorDAO.findAll();
        }


        if (equipos.isEmpty() || patrocinadores.isEmpty()) {
            System.out.println("No hay equipos o patrocinadores para generar fichajes.");
        } else {

            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaFin = fechaInicio.plusYears(1);

            int limite = Math.min(equipos.size(), patrocinadores.size());

            for (int i = 0; i < limite; i++) {

                double cantidad = Math.round(
                        (100000 + (Math.random() * 500000)) * 100.0
                ) / 100.0;

                Patrocinio patrocinio = new Patrocinio(
                        equipos.get(i),
                        patrocinadores.get(i),
                        cantidad,
                        fechaInicio,
                        fechaFin
                );

                patrocinioDAO.save(patrocinio);
            }

            System.out.println("Fichajes generados correctamente.");
        }



        // =========================================================
        // =================== CONSULTAS ============================
        // =========================================================

        // -------------------------------
        // CONSULTA 1
        // -------------------------------
        System.out.println("\n--- CONSULTA 1 ---");

        List<Object[]> datos = competicionDAO.obtenerCaracteristicasCompeticion();
        for (Object[] fila : datos) {

            String nombre = (String) fila[0];
            LocalDate fecha = ((java.sql.Date) fila[1]).toLocalDate();
            Integer numEquipos = ((Number) fila[2]).intValue();
            Integer numJornadas = ((Number) fila[3]).intValue();

            System.out.println("Nombre: " + nombre);
            System.out.println("Fecha creación: " + fecha);
            System.out.println("Nº Equipos: " + numEquipos);
            System.out.println("Nº Jornadas: " + numJornadas);
            System.out.println("----------------------------");
        }

        // -------------------------------
        // CONSULTA 2
        // -------------------------------
        System.out.println("\n--- CONSULTA 2 ---");

        List<Equipo> equiposCompeticion =
                equipoDAO.obtenerEquiposDeCompeticion(competicion.getId());

        for (Equipo e : equiposCompeticion) {
            System.out.println("Equipo: " + e.getNombre() +
                    " | Región: " + e.getRegion());
        }

        // -------------------------------
        // CONSULTA 3
        // -------------------------------
        System.out.println("\n--- CONSULTA 3 ---");

        Equipo equipoEjemplo = equipos.get(0);
        List<Jugador> jugadores =
                jugadorDAO.obtenerJugadoresPorEquipo(equipoEjemplo.getId());

        for (Jugador j : jugadores) {
            System.out.println("Jugador: " + j.getNombre() +
                    " | Nacionalidad: " + j.getNacionalidad());
        }

        // -------------------------------
        // CONSULTA 4
        // -------------------------------
        System.out.println("\n--- CONSULTA 4 ---");

        PatrocinadorDAO patrocinadorDAO1 = new PatrocinadorDAO();
        List<Patrocinador> patrocinadores2 = patrocinadorDAO1.obtenerPatrocinadoresDeEquipo(equipoEjemplo.getId());

        for (Patrocinador p : patrocinadores2) {
            System.out.println("Patrocinador: " + p.getNombre());
        }

        // -------------------------------
        // CONSULTA 5
        // -------------------------------
        System.out.println("\n--- CONSULTA 5 ---");

        Long equipoIdConsulta5 = equipoEjemplo.getId();

        List<Jugador> jugadoresEquipo =
                jugadorDAO.obtenerJugadoresPorEquipo(equipoIdConsulta5);

        PatrocinioDAO patrocinioDAO2 = new PatrocinioDAO();
        List<Patrocinador> patrocinadoresEquipo =
                patrocinioDAO2.obtenerPatrocinadoresPorEquipo(equipoIdConsulta5);

        System.out.println("Jugadores:");
        for (Jugador j : jugadoresEquipo) {
            System.out.println("- " + j.getNombre());
        }

        System.out.println("Patrocinadores:");
        for (Patrocinador p : patrocinadoresEquipo) {
            System.out.println("- " + p.getNombre());
        }

        // -------------------------------
        // CONSULTA 6
        // -------------------------------
        System.out.println("\n--- CONSULTA 6 ---");

        Double edadMedia =
                jugadorDAO.obtenerEdadMediaPorEquipo(equipoIdConsulta5);

        if (edadMedia != null) {
            System.out.println("Edad media: " +
                    String.format("%.2f", edadMedia));
        }

        // -------------------------------
        // CONSULTA 7
        // -------------------------------
        System.out.println("\n--- CONSULTA 7 ---");

        List<Object[]> resultado7 =
                jugadorDAO.contarJugadoresMayoresDe23PorNacionalidad();

        for (Object[] fila : resultado7) {
            System.out.println("Nacionalidad: " + fila[0] +
                    " | Cantidad: " + fila[1]);
        }

        // -------------------------------
        // CONSULTA 8
        // -------------------------------
        System.out.println("\n--- CONSULTA 8 ---");

        ClasificacionDAO clasificacionDAO = new ClasificacionDAO();

        int totalJornadas = competicion.getCantidadJornadas();
        int jornadaFinal = totalJornadas;

        mostrarClasificacion(clasificacionDAO, temporada, 1, "INICIO");
        mostrarClasificacion(clasificacionDAO, temporada, jornadaFinal, "FINAL");

        // -------------------------------
        // CONSULTA 9
        // -------------------------------
        System.out.println("\n--- CONSULTA 9: Top 3 y Bottom 3 ---");

        List<ClasificacionInicial> top3 =
                clasificacionDAO.obtenerTopEquipos(temporada, jornadaFinal, false);

        System.out.println("\nTOP 3:");
        for (ClasificacionInicial c : top3) {
            System.out.println(c.getEquipo().getNombre() +
                    " | Puntos: " + c.getPuntos());
        }

        List<ClasificacionInicial> bottom3 =
                clasificacionDAO.obtenerTopEquipos(temporada, jornadaFinal, true);

        System.out.println("\nBOTTOM 3:");
        for (ClasificacionInicial c : bottom3) {
            System.out.println(c.getEquipo().getNombre() +
                    " | Puntos: " + c.getPuntos());
        }

        // ===============================
        // CONSULTA 10
        // ===============================
        System.out.println("\n--- CONSULTA 10: Nuevas incorporaciones ---");

        List<Jugador> nuevas = jugadorDAO.obtenerNuevasIncorporaciones();

        if (nuevas.isEmpty()) {
            System.out.println("No hay nuevas incorporaciones.");
        } else {
            for (Jugador j : nuevas) {
                System.out.println(
                        j.getNombre() +
                                " | Nacionalidad: " + j.getNacionalidad()
                );
            }
        }

        // ===============================
        // CONSULTA 11
        // ===============================
        System.out.println("\n--- CONSULTA 11: Fichajes realizados ---");

        PatrocinioDAO patrocinioDAO3 = new PatrocinioDAO();

        List<Patrocinio> fichajes = patrocinioDAO3.obtenerTodosLosFichajes();

        if (fichajes.isEmpty()) {
            System.out.println("No hay fichajes registrados.");
        } else {
            for (Patrocinio p : fichajes) {
                System.out.println(
                        p.getEquipo().getNombre() +
                                " fichó a " +
                                p.getPatrocinador().getNombre() +
                                " el " +
                                p.getFechaInicio() +
                                " | Cantidad: " +
                                p.getCantidad()
                );
            }
        }

        // ===============================
        // CONSULTA 12
        // ===============================
        System.out.println("\n--- CONSULTA 12: Total de deportistas en la competición ---");

        Long totalJugadores =
                jugadorDAO.contarJugadoresPorCompeticion(competicion.getId());

        System.out.println("Total de jugadores: " + totalJugadores);

        // ===============================
        // CONSULTA 13
        // ===============================
        System.out.println("\n--- CONSULTA 13: Patrocinadores comunes ---");

        if (equipos.size() >= 2) {

            Equipo equipo1 = equipos.get(0);
            Equipo equipo2 = equipos.get(1);

            PatrocinioDAO patrocinioDAO4 = new PatrocinioDAO();

            List<Patrocinador> comunes =
                    patrocinioDAO4.obtenerPatrocinadoresComunes(
                            equipo1.getId(),
                            equipo2.getId()
                    );

            if (comunes.isEmpty()) {
                System.out.println("No tienen patrocinadores comunes.");
            } else {
                for (Patrocinador p : comunes) {
                    System.out.println("Patrocinador común: " + p.getNombre());
                }
            }
        }

        // ===============================
        // CONSULTA 14
        // ===============================
        System.out.println("\n--- CONSULTA 14: CriteriaQuery ---");

        JugadorDAO jugadorDAO14 = new JugadorDAO();

    // EJEMPLO 1 (TODOS LOS ATRIBUTOS)
        System.out.println("\nEjemplo 1 (todos los filtros):");

        List<Jugador> ejemplo1 =
                jugadorDAO14.filtrarJugadoresCriteria(
                        "a",              // nombre contiene
                        "Spain",          // nacionalidad
                        20,               // edad mínima
                        30,               // edad máxima
                        equipos.get(0).getId(), // equipo
                        true              // ordenar asc
                );

        for (Jugador j : ejemplo1) {
            System.out.println(j.getNombre() + " | " + j.getNacionalidad());
        }


        //  EJEMPLO 2 (solo edad)
        System.out.println("\nEjemplo 2 (solo edad > 25):");

        List<Jugador> ejemplo2 =
                jugadorDAO14.filtrarJugadoresCriteria(
                        null,
                        null,
                        25,
                        null,
                        null,
                        true
                );

        for (Jugador j : ejemplo2) {
            System.out.println(j.getNombre());
        }


        // EJEMPLO 3 (solo equipo y nacionalidad)
        System.out.println("\nEjemplo 3 (equipo + nacionalidad):");

        List<Jugador> ejemplo3 =
                jugadorDAO14.filtrarJugadoresCriteria(
                        null,
                        "Korea",
                        null,
                        null,
                        equipos.get(1).getId(),
                        false
                );

        for (Jugador j : ejemplo3) {
            System.out.println(j.getNombre());
        }



    }

    private void mostrarClasificacion(ClasificacionDAO dao,
                                      Temporada temporada,
                                      int jornadaNumero,
                                      String etiqueta) {

        List<ClasificacionInicial> clasificacion =
                dao.obtenerClasificacionPorJornada(temporada, jornadaNumero);

        if (clasificacion.isEmpty()) {
            System.out.println("No hay datos para " + etiqueta);
            return;
        }

        System.out.println("\nCLASIFICACIÓN " + etiqueta);

        for (ClasificacionInicial c : clasificacion) {
            System.out.println(
                    c.getEquipo().getNombre() +
                            " | Puntos: " + c.getPuntos()
            );
        }

    }





}



