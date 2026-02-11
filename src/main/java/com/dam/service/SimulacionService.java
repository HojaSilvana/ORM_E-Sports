package com.dam.service;

import com.dam.dao.*;
import com.dam.model.*;

import java.time.LocalDate;
import java.util.List;

public class SimulacionService {

    private CompeticionDAO competicionDAO = new CompeticionDAO();
    private TemporadaDAO temporadaDAO = new TemporadaDAO();
    private EquipoDAO equipoDAO = new EquipoDAO();
    private PartidoDAO partidoDAO = new PartidoDAO();
    private JornadaDAO jornadaDAO = new JornadaDAO();




    public void iniciarSimulacion() {

        // Aqui vamos a crear la competicion
        Competicion competicion = new Competicion(
                "League of Legends World Cup",
                LocalDate.now(),
                3,
                4
        );

        competicionDAO.save(competicion);

        // Una vez creada la competicion, creamos una temporada nueva
        Temporada temporada = new Temporada(2026, competicion);
        temporadaDAO.save(temporada);

        System.out.println("Simulación creada correctamente");

        for (int i = 1; i <= competicion.getCantidadJornadas(); i++) {
            Jornada jornada = new Jornada(i, temporada);
            jornadaDAO.save(jornada);
        }

        System.out.println("Jornadas creadas correctamente");

        List<Equipo> equipos = equipoDAO.findAll();
        List<Jornada> jornadas = jornadaDAO.findAll();

        for (Jornada jornada : jornadas) {

            for (int i = 0; i < equipos.size(); i += 2) {

                if (i + 1 < equipos.size()) {
                    Equipo local = equipos.get(i);
                    Equipo visitante = equipos.get(i + 1);

                    Partido partido = new Partido(jornada, local, visitante);

                    // Resultado aleatorio simple
                    partido.setScoreEquipoLocal((int) (Math.random() * 5));
                    partido.setScoreEquipoVisitante((int) (Math.random() * 5));

                    partidoDAO.save(partido);
                }
            }
        }

        System.out.println("Partidos generados correctamente");

        new ClasificacionService().calcularClasificacion(temporada);



    }
}
