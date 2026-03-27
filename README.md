# Cambios por error de diseño
- El atributo DNI de la tabla T_PERSONS va a ser UNIQUE porque sino no puede ser clave foranea en la tabla T_CONTACTS (pese a que se diga que se va a comprobar que sea única en la lógica de negocio).
- La clave primaria de la tabla T_CONTACTS va a ser solo el ID_PERSON. DNI va a ser solo clave foranea puesto que ya comprobaremos que no se repita ningún DNI en la lógica de negocio. 

- Utilizar optionals en los DTOs es mala práctica porque solo aprota complejidad y ninguna ventaja. En nuestro caso SI que vamos a devolver nulls pero documentándolo. De esta forma el trabajo de mapeado de  Jackson va a ser mas sencillo.

- Variable DNI en PersonaDTO no sigue los estándares. Para adequarla tiene que estar escrita toda en mayúscula. Si no la renombramos vamos a tener problemas con Jackson al deserializar los objetos PersonaDTO. El atributo de la tabla si que puede seguir siendo DNI.