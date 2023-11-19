# Prueba Técnica Ingeneo


### Filtrados y Páginado de las consultas de las tablas
Se explicara los 4 parametros que se tienen para realizar este tipo de funcionalidad en el cual las rutas que devuelven las listas para mostrarse en un tabla por ejemplo se tiene:

1. **page (Requerido)**: La posicion del paginado empezando con el valor 0 como la primera páginacion.
2. **size (Requerido)**: Cantidad de registros que se quiere filtrar.
3. El parametro **filter** esta conformado:

	Sigue el patron de REST Query Language with Spring Data JPA Specifications.

	Sigue el siguiente patron de 3 parametros separados por simbolos siendo:

	- key: string de la propiedad que se quiere filtra.
	- operation: una lista de posibles valores de los cuales tenemos disponible:
		- ":" valor exacto o si es string like.
		- "<" lessThanOrEqualTo.
		- ">" greaterThanOrEqualTo.
		- "-in-" lista de valores.
		- "-bw-" between entre dos fechas.

	- value: valor exacto o si son los dos ultimos separados por +.
	
		Ejemplos:
        
			1. nombres:Jos (Filtro  nombre like '%jos%')
			2. cantidadProducto:12 ( filtro cantidadProducto = 15)
			3. tipoTransporteId:1 (Al ser un id hace referencia a ser una relacion a nivel de bdd entonces se hace una transformacion a nivel del backend en el ejemplo siguiendo destinoEntrega.tipoTransporte.id = 1; dependera de la entidad)
			4. paisId-in-1+2 (igual que caso anterior siendo ejemplo del fitro ya en el backEnd de acuerdo a la entidad pais.id in (1,2) )
			5. fechaEntrega-bw-01/12/2023+31/12/2023 (Filtro del tipo FECHA_ENTREGA between DATE(01/12/2023) and DATE(31/12/202))
			6. id:2,nombres:Jos,cantidadProducto:12 (Combinacion de los 3 filtros que se explicaron anteriormente)
			
4. **sort**: es la propiedad que se quiere realizar el order by siendo los valores compuesto:
		"propiedad,desc | asc" (case insensitive e incluir la coma para separar propiedad y el tipo)".
        
		Ejemplo:
			1. cantidadProducto, asc (ordenamiento por cantidadProducto ascendente)


------------
### Security API
La seguridad de la API esta por medio de JWT version 0.11.5 por lo cual las consideraciones especiales en este apartado son:
- Los unicos endPoint's que se dejaron libre son los siguientes https://contextApp/api/v1/auth/**
- El metodo para iniciar sesión y que genere el token del tipo Bearer es https://contextApp/api/v1/auth/login del tipo **POST**.
- Dependiendo del Rol del usuario del sistema asi mismo se habilita los recursos que este puede acceder por medio de Enables **Spring Security Method Security**.

------------