package com.dam.app;


import com.dam.service.SimulacionService;
import com.dam.util.CargaDatosIniciales;

/**
 * Esta clase se encarga de iniciar la simulación de la competición.
 *
 * @author David
 */
public class SimulacionMain {

    public static void main(String[] args) {

        new SimulacionService().iniciarSimulacion();

    }
}
