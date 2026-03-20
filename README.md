# Cambios por error de diseño
- El atributo DNI de la tabla T_PERSONS va a ser UNIQUE porque sino no puede ser clave foranea en la tabla T_CONTACTS (pese a que se diga que se va a comprobar que sea única en la lógica de negocio).
- La clave primaria de la tabla T_CONTACTS va a ser solo el ID_PERSON. DNI va a ser solo clave foranea puesto que ya comprobaremos que no se repita ningún DNI en la lógica de negocio. 
- Utilizar optionals en los parámetros de los DTO no tiene sentido ya que solo aporta complejidad al código sin ninguna ventaja. Solo voy a utilizarlo en los getters que sean opcionales