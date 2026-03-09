# ⚡ Energy Management Backend API

Backend desarrollado con **Spring Boot + PostgreSQL + JWT** para el
análisis y consulta de datos energéticos a partir de datasets de
energías renovables.

Esta API permite: - Autenticación segura con **JWT** - Registro e inicio
de sesión de usuarios - Carga automática de datasets CSV - Consultas
estadísticas sobre energía - Filtrado dinámico por **año, región y tipo
de energía** - Documentación automática con **Swagger / OpenAPI**

El proyecto está pensado para ser consumido por un **frontend web o
dashboard analítico**.

------------------------------------------------------------------------

# Tecnologías Utilizadas

  Tecnología          Versión
  ------------------- ---------
  Java                17
  Spring Boot         3.2.3
  Spring Security     6.x
  PostgreSQL          14+
  Maven               3.8+
  JWT (jjwt)          0.11.5
  Swagger / OpenAPI   2.3.0
  Lombok              Última

------------------------------------------------------------------------

# Arquitectura del Proyecto

El proyecto sigue una arquitectura **por capas de Spring Boot**.

    com.energy
    │
    ├── Config
    │   ├── ApplicationConfig
    │   ├── DataInitializer
    │   ├── JwtFilter
    │   ├── JwtUtil
    │   ├── SecurityConfig
    │   ├── SwaggerConfig
    │   └── UserInitializer
    │
    ├── Controller
    │   ├── AuthController
    │   └── EnergyController
    │
    ├── DTO
    │   ├── LoginRequest
    │   ├── RegisterRequest
    │   ├── AuthResponse
    │   ├── StatResponseDTO
    │   ├── CapacityEvolutionDTO
    │   └── RegionalCompareDTO
    │
    ├── Model
    │   ├── EnergyModel
    │   ├── EnergyType
    │   └── UserModel
    │
    ├── Repository
    │   ├── EnergyRepository
    │   └── UserRepository
    │
    ├── Service
    │   ├── AuthService
    │   ├── CsvService
    │   └── EnergyService
    │
    └── BackendApplication

------------------------------------------------------------------------

# Clase Principal

## BackendApplication

Clase que inicia la aplicación Spring Boot.

Responsable de: - Inicializar el contexto de Spring - Cargar todas las
dependencias - Arrancar el servidor web

------------------------------------------------------------------------

# Configuración

## ApplicationConfig

Configura los componentes de autenticación:

-   UserDetailsService
-   AuthenticationProvider
-   PasswordEncoder

Permite que Spring Security obtenga los usuarios desde la base de datos.

------------------------------------------------------------------------

## SecurityConfig

Define:

-   rutas públicas
-   rutas protegidas
-   política **stateless**
-   integración con **JWT**

Rutas públicas:

    /auth/**
    /swagger-ui/**
    /v3/api-docs/**

Todas las demás rutas requieren autenticación.

------------------------------------------------------------------------

## JwtUtil

Clase encargada de:

-   generar tokens JWT
-   validar tokens
-   extraer el usuario desde el token

------------------------------------------------------------------------

## JwtFilter

Filtro que intercepta cada request HTTP para verificar el token JWT
antes de permitir acceso al endpoint.

------------------------------------------------------------------------

## SwaggerConfig

Configura **Swagger / OpenAPI** para documentar la API.

Acceso:

    http://localhost:8081/swagger-ui.html

------------------------------------------------------------------------

## DataInitializer

Carga automáticamente los datasets CSV al iniciar el backend.

Archivos cargados:

-   installed_solar_capacity.csv
-   modern-renewable-prod.csv
-   share_electricity_renewables.csv

Los datos se insertan automáticamente en la tabla:

    energy_data

------------------------------------------------------------------------

## UserInitializer

Crea automáticamente un **usuario administrador** si no existe.

Credenciales por defecto:

Email:

    admin@energy.com

Password:

    Admin

Rol:

    ADMIN

------------------------------------------------------------------------

# 🗄 Modelos de Datos

## EnergyModel

Representa los datos energéticos.

Campos:

  Campo         Descripción
  ------------- -----------------------
  id            identificador
  energyType    tipo de energía
  region        país o región
  production    producción energética
  consumption   consumo energético
  capacity      capacidad instalada
  yearData      año

------------------------------------------------------------------------

## EnergyType

Enum que define los tipos de energía soportados.

    SOLAR
    RENEWABLE
    SHARE

------------------------------------------------------------------------

## UserModel

Entidad de usuario usada para autenticación.

Implementa:

    UserDetails

Campos:

    id
    email
    password
    role
    enabled

------------------------------------------------------------------------

# Controladores REST

## AuthController

Endpoints de autenticación.

  Endpoint         Método   Descripción
  ---------------- -------- ---------------------------
  /auth/register   POST     Registrar usuario
  /auth/login      POST     Login y generación de JWT

------------------------------------------------------------------------

## EnergyController

Endpoints para consulta de datos energéticos.

  Endpoint                               Descripción
  -------------------------------------- ----------------------------
  /api/energy/filter                     Filtrar datos
  /api/energy/stats/production-type      Producción por tipo
  /api/energy/stats/top-consumption      Regiones con mayor consumo
  /api/energy/stats/capacity-evolution   Evolución de capacidad
  /api/energy/stats/regional-compare     Producción vs consumo
  /api/energy/data/{type}                Datos por tipo
  /api/energy/regions                    Lista de regiones
  /api/energy/years                      Lista de años
  /api/energy/types                      Tipos de energía

------------------------------------------------------------------------

# DTOs

Los DTOs permiten devolver datos estructurados en lugar de arrays sin
nombres.

### StatResponse

    label
    value

------------------------------------------------------------------------

### CapacityEvolution

    year
    averageCapacity

------------------------------------------------------------------------

### RegionalCompare

    region
    production
    consumption

------------------------------------------------------------------------

### AuthResponse

Respuesta del login.

Ejemplo:

``` json
{
 "token": "JWT_TOKEN",
 "email": "user@email.com",
 "role": "USER"
}
```

------------------------------------------------------------------------

# Base de Datos

Motor:

    PostgreSQL

Base de datos:

    energy_db

Tablas generadas:

    energy_data
    users

------------------------------------------------------------------------

# Configuración Local

Editar:

    src/main/resources/application.properties

Ejemplo:

    server.port=8081

    spring.datasource.url=jdbc:postgresql://localhost:5432/energy_db
    spring.datasource.username=postgres
    spring.datasource.password=123456

    spring.jpa.hibernate.ddl-auto=create
    spring.jpa.show-sql=true

⚠ Ajustar usuario y contraseña según tu entorno.

------------------------------------------------------------------------

# Cómo Ejecutar el Proyecto

## 1 Clonar repositorio

    git clone https://github.com/usuario/repositorio.git

------------------------------------------------------------------------

## 2 Crear base de datos

En PostgreSQL:

    CREATE DATABASE energy_db;

------------------------------------------------------------------------

## 3 Ejecutar backend

Con Maven:

    mvn spring-boot:run

O ejecutar la clase:

    BackendApplication

------------------------------------------------------------------------

## 4 Acceder a Swagger

Abrir en navegador:

    http://localhost:8081/swagger-ui.html

------------------------------------------------------------------------

# Autenticación

1️⃣ Registrar usuario

    POST /auth/register

2️⃣ Login

    POST /auth/login

3️⃣ Copiar token

4️⃣ Usar token en Swagger:

    Authorization: Bearer TOKEN

------------------------------------------------------------------------

# Flujo de Uso de la API

1️⃣ Login

    POST /auth/login

2️⃣ Obtener filtros

    GET /api/energy/types
    GET /api/energy/regions
    GET /api/energy/years

3️⃣ Consultar datos

    /api/energy/filter?year=2021&type=SOLAR

4️⃣ Consultar estadísticas

    /api/energy/stats/production-type
    /api/energy/stats/top-consumption
    /api/energy/stats/capacity-evolution
    /api/energy/stats/regional-compare

------------------------------------------------------------------------


# Autor

Proyecto académico desarrollado para análisis de datasets energéticos
utilizando **Spring Boot + PostgreSQL + JWT**.
