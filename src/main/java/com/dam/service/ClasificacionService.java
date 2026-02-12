package com.dam.service;

import com.dam.dao.ClasificacionDAO;
import com.dam.dao.PartidoDAO;
import com.dam.model.*;

import java.util.*;

/**
 * Esta clase se encarga de calcular y guardar
 * la clasificación tras cada jornada.
 *
 * @author David Cuenca
 */


public class ClasificacionService {

    private PartidoDAO partidoDAO = new PartidoDAO();
    private ClasificacionDAO clasificacionDAO = new ClasificacionDAO();

    public void calcularClasificacion(Temporada temporada, Jornada jornada) {

        Set<Equipo> equipos = temporada.getEquipos();

        Map<Equipo, int[]> tabla = new HashMap<>();

        // Inicializamos tabla acumulada
        for (Equipo equipo : equipos) {
            tabla.put(equipo, new int[]{0, 0, 0, 0});
        }

        // Solo partidos de ESTA jornada
        List<Partido> partidosJornada =
                partidoDAO.findByJornada(jornada.getId());

        for (Partido partido : partidosJornada) {

            Equipo local = partido.getEquipoLocal();
            Equipo visitante = partido.getEquipoVisitante();

            int golesLocal = partido.getScoreEquipoLocal();
            int golesVisitante = partido.getScoreEquipoVisitante();

            tabla.get(local)[1]++;
            tabla.get(visitante)[1]++;

            if (golesLocal > golesVisitante) {
                tabla.get(local)[0] += 3;
                tabla.get(local)[2]++;
                tabla.get(visitante)[3]++;
            } else if (golesLocal < golesVisitante) {
                tabla.get(visitante)[0] += 3;
                tabla.get(visitante)[2]++;
                tabla.get(local)[3]++;
            } else {
                tabla.get(local)[0] += 1;
                tabla.get(visitante)[0] += 1;
            }
        }

        // Guardamos clasificación de ESTA jornada
        for (Equipo equipo : equipos) {

            int[] datos = tabla.get(equipo);

            ClasificacionInicial clasificacion =
                    new ClasificacionInicial(
                            datos[0],
                            datos[1],
                            datos[2],
                            datos[3],
                            temporada,
                            equipo,
                            jornada
                    );

            clasificacionDAO.save(clasificacion);
        }

        System.out.println("Clasificación guardada para jornada " + jornada.getNumero());
    }

}


