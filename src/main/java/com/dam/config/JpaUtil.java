package com.dam.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Esta clase se encarga de gestionar la configuración
 * y creación del EntityManager para JPA.
 *
 * @author David Cuenca
 */


public class JpaUtil {

    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("orm-sports");
        } catch (Exception e) {
            System.err.println("Error creating EntityManagerFactory");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
