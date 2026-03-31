# Changelog
## 0.9.7 - 2026-03-31
### Added
- Tests para Contacto (`ContactoTest`) y Persona (`PersonaTest`)
## 0.9.6 - 2026-03-31
### Added
- Tests del mapper (`MapperTest`).

## 0.9.5 - 2026-03-31
### Added
- JaCoCo para reporte de cobertura de tests (plugin Maven).

## 0.9.4 - 2026-03-31
### Added
- Batería de tests “necesarios” para reforzar la cobertura (DTOs, servicios, validadores y controlador).

## 0.9.3 - 2026-03-31
### Added
- Javadoc en los ficheros principales del proyecto.

## 0.9.2 - 2026-03-27
### Added
- Restricción personalizada de DNI:
  - Anotación `@Dni`.
  - `DniValidator` con validación de formato y letra de control.
  - Test de validación de la anotación (`DniAnnotationValidationTest` / `DniValidatorTest`).

## 0.9.1 - 2026-03-27
### Added
- Validaciones adicionales (Bean Validation) en DTOs/capa web.

## 0.9.0 - 2026-03-27
### Added
- Tests:
  - Deserialización de DTOs.
  - Endpoints del controlador (`PersonasApiControllerTest`).

## 0.8.0 - 2026-03-23
### Added
- Finalización de DTOs y del modelo de deserialización JSON (builders).
- Añadidos los siguientes endpoints:
  - `POST /personas`: crear una nueva persona (validación Bean Validation con `@Valid`).
  - `PUT /personas/{DNI}`: actualizar una persona (obliga a que el DNI de la ruta coincida con el DNI del cuerpo; devuelve 400 en actualizaciones inválidas).
- OpenAPI/Swagger UI mediante `springdoc-openapi-starter-webmvc-ui`.

## 0.7.0 - 2026-03-20
### Changed
- Eliminación de `Optional` en DTOs para simplificar deserialización/mapeo (se documenta el uso de `null` cuando aplica).
- Capa de servicios:
  - `PersonaServices` con la lógica de negocio para CRUD de personas.

## 0.6.1 - 2026-03-20
### Removed
- Limpieza de dependencias no utilizadas.
### Added
- Utilidades de mapeo (`Mapper`) entre entidades de persistencia (`Persona`, `Contacto`) y DTOs de la API, incluyendo el campo derivado `fullName`.

## 0.6.0 - 2026-03-20
### Added
- Endpoints `GET` y `DELETE` operativos para `/personas` (por DNI y borrado masivo).

## 0.5.0 - 2026-03-20
### Added
- Inicialización de base de datos con datos de ejemplo (`data.sql`).
- Persistencia con repositorios Spring Data JPA (`PersonaRepository`, `ContactoRepository`) y base de datos H2 en memoria.

## 0.4.0 - 2026-03-20
### Changed
- Ajustes de diseño en modelos para cumplir cambios de la práctica:
  - `DNI` pasa a ser `UNIQUE` en `T_PERSONS`.
  - `T_CONTACTS` usa PK compartida `ID_PERSON` (y `DNI` queda como FK adicional).
  - Se mantiene `DNI` en contactos por requisitos de diseño.

### Added
- README con aclaraciones sobre decisiones/cambios de diseño.

## 0.3.0 - 2026-03-18
### Added
- Controlador REST inicial `PersonasApiController` (plantilla de endpoints).

## 0.2.0 - 2026-03-18
### Added
- Capa de DTOs:
  - `PersonaDTO`: DTO inmutable con deserialización JSON basada en builder.
  - `ContactDetailsDTO`: DTO inmutable con deserialización JSON basada en builder.

## 0.1.0 - 2026-03-18
### Added
- Estructura inicial del proyecto Spring Boot con Maven y configuración base (`pom.xml`, `application.properties`).
- Modelos JPA iniciales `Persona` y `Contacto` (incluyendo clave compuesta inicial `ContactoId`).
