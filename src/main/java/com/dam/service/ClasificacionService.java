package com.dam.service;

import com.dam.dao.*;
import com.dam.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta clase se encarga de calcular y almacenar
 * la clasificación general de una temporada.
 *
 * @author David Cuenca
 */
public class ClasificacionService {

    private PartidoDAO partidoDAO = new PartidoDAO();
    private EquipoDAO equipoDAO = new EquipoDAO();
    private ClasificacionDAO clasificacionDAO = new ClasificacionDAO();

    public void calcularClasificacion(Temporada temporada) {

        List<Partido> partidos = partidoDAO.findAll();
        List<Equipo> equipos = equipoDAO.findAll();

        // Mapa para acumular estadísticas
        Map<Equipo, int[]> tabla = new HashMap<>();

        for (Equipo equipo : equipos) {
            tabla.put(equipo, new int[]{0, 0, 0, 0});
            // [0]=puntos, [1]=jugados, [2]=victorias, [3]=derrotas
        }

        for (Partido partido : partidos) {

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

        // Guardar clasificación en BD
        for (Equipo equipo : equipos) {

            int[] datos = tabla.get(equipo);

            ClasificacionInicial clasificacion =
                    new ClasificacionInicial(
                            datos[0],
                            datos[1],
                            datos[2],
                            datos[3],
                            temporada,
                            equipo
                    );

            clasificacionDAO.save(clasificacion);
        }

        System.out.println("Clasificación calculada y guardada correctamente.");
    }
}
