# ORM E-Sports – Simulación de Competición (V1)

Aplicación desarrollada en **Java 25** utilizando **Jakarta Persistence 3.1 (JPA)** con **Hibernate 6.x** como proveedor ORM y base de datos **MySQL**.

El sistema modela una competición de eSports inspirada en League of Legends y permite generar temporadas, jornadas, partidos, clasificaciones y ejecutar consultas avanzadas mediante JPQL y Criteria API.

---

##  Características actuales

- Modelo completo de entidades JPA con relaciones complejas
- Generación automática de temporada y jornadas
- Simulación de partidos con resultados aleatorios
- Cálculo automático de clasificación
- 14 consultas implementadas con JPQL
- Consultas dinámicas mediante Criteria API
- Arquitectura por capas (model, dao, service, util)
- Gestión de dependencias con Maven
- Control de versiones con Git

---

##  Arquitectura del proyecto

El proyecto sigue una separación clara de responsabilidades:

- **model** → Entidades JPA y mapeo relacional
- **dao** → Acceso a datos y consultas
- **service** → Lógica de negocio y simulación
- **util** → Configuración y carga de datos

---


##  Estructura del proyecto

```
com.orm.esports
│
├── model
│   ├── Competicion.java
│   ├── Temporada.java
│   ├── Jornada.java
│   ├── Partido.java
│   ├── Equipo.java
│   ├── Jugador.java
│   ├── Estadio.java
│   ├── Patrocinador.java
│   ├── Patrocinio.java
│   └── ClasificacionInicial.java
│
├── dao
│   ├── EquipoDAO.java
│   ├── PartidoDAO.java
│   └── ...
│
├── service
│   ├── SimulacionService.java
│   └── ClasificacionService.java
│
├── util
│   ├── JpaUtil.java
│   └── CargaDatosIniciales.java
│
└── Main.java
```



---

## Tecnologías utilizadas

- Java 25
- Maven
- Jakarta Persistence 3.1
- Hibernate 6.x
- MySQL
- Log4j

---

##  Estado actual

- ✔ Modelo completamente funcional
- ✔ Simulación operativa
- ✔ Clasificación calculada correctamente
- ✔ Consultas JPQL implementadas
- ✔ Consultas dinámicas con Criteria API


