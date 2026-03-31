# 
# Práctica API REST - Gestión de Personas y Contactos (Spring Boot)

API REST desarrollada con **Spring Boot** para la **gestión de personas** y sus **personas de contacto**.  
Incluye **capa de servicios**, **DTOs**, **entidades JPA** y base de datos **H2**.

## Tecnologías

- Java **17**
- Spring Boot **4.0.3**
- Persistencia: Spring Data JPA + **H2**
- Tests y cobertura: **JaCoCo 0.8.12**
- Build: Maven

## Funcionalidades (resumen)

- Gestión de personas.
- Gestión de contactos asociados a una persona.
- Validaciones (p. ej. formato de DNI mediante anotación/validador).
- Persistencia con JPA sobre base de datos H2.

## Estructura del proyecto

- `controllers/`: endpoints REST.
- `services/`: lógica de negocio.
- `dtos/`: objetos de transferencia (entrada/salida).
- `models/`: entidades JPA.
- `repositories/`: acceso a datos (Spring Data).
- `validators/` y `annotations/`: validaciones personalizadas.

## Requisitos

- JDK 17
- Maven 3.9+ (o usar `mvnw`/`mvnw.cmd`)

## Configuración

La configuración principal está en:

- `src/main/resources/application.properties`

Carga de datos inicial (si aplica):

- `src/main/resources/data.sql`

## Ejecución

En Windows (cmd):

```bash
mvnw.cmd spring-boot:run
```

En Linux/macOS:

```bash
./mvnw spring-boot:run
```

La API quedará accesible en el puerto configurado (por defecto suele ser `8080`).

## Tests y cobertura

Ejecutar tests:

```bash
mvnw.cmd test
```

Generar informe de cobertura JaCoCo:

```bash
mvnw.cmd verify
```

El informe se genera típicamente en:

- `target/site/jacoco/index.html`

## Base de datos (H2)

La aplicación utiliza **H2** como base de datos en memoria/embebida (según configuración).  
Si está habilitada la consola de H2, se podrá acceder desde el navegador (ver `application.properties`).

## Changelog

Ver [`CHANGELOG.md`](CHANGELOG.md).

## Contribuciones

Este proyecto **no acepta contribuciones** externas.

## Licencia

Licenciado bajo **Apache License 2.0**. Ver `LICENSE` o la cabecera de licencia correspondiente del repositorio si aplica.

---

## Notas de diseño / Cambios por error de diseño

- El atributo **DNI** de la tabla `T_PERSONS` va a ser **UNIQUE** porque si no, no puede ser clave foránea en la tabla `T_CONTACTS` (pese a que se diga que se va a comprobar que sea única en la lógica de negocio).
- La clave primaria de la tabla `T_CONTACTS` va a ser solo el `ID_PERSON`. `DNI` va a ser solo clave foránea puesto que ya comprobaremos que no se repita ningún DNI en la lógica de negocio. Lo más correcto sería que `DNI` desapareciera de esta tabla ya que es accesible utilizando el `id_person` pero lo vamos a mantener como clave foránea por si alguna funcionalidad especial lo requiriera.
- Utilizar `Optional` en los DTOs es mala práctica porque solo aporta complejidad y ninguna ventaja. En nuestro caso sí que vamos a devolver `null` pero documentándolo. De esta forma el trabajo de mapeado de Jackson va a ser más sencillo.
- La variable `DNI` en `PersonaDTO` no sigue los estándares. Para adecuarla tiene que estar escrita toda en mayúscula. Si no la renombramos vamos a tener problemas con Jackson al deserializar los objetos `PersonaDTO`. El atributo de la tabla sí que puede seguir siendo `DNI`.
- Declaramos `telefono` como un número como lo indica la práctica pero lo más apropiado sería declararlo como un `String` para poder aceptar códigos de países (ej: `+34 656 656 656`).
