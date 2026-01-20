package com.dam.app;

import com.dam.config.JpaUtil;
import com.dam.model.Equipo;
import com.dam.model.Estadio;
import com.dam.model.Jugador;
import com.dam.util.CargaDatosIniciales;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;

/**
 * Esta clase se encarga de iniciar la simulación de la competición.
 *
 * @author David
 */
public class SimulacionMain {

    public static void main(String[] args) {
        CargaDatosIniciales.cargar();
    }
}
