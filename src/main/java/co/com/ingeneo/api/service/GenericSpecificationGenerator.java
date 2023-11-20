package co.com.ingeneo.api.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.jpa.domain.Specification;

import co.com.ingeneo.api.repository.domain.HasIdGetter;
import co.com.ingeneo.api.repository.filter.GenericSpec;
import co.com.ingeneo.api.repository.filter.GenericSpecBuilder;

/**
 * Clase intermedia para obtener un Specification<Clase Entidad que implementa HasIdGetter>, se ocupa para realizar filtrado de datos 
 * @author Josue Menendez
 * @param <T> Clase Entidad que implementa HasIdGetter
 */
public class GenericSpecificationGenerator <T extends HasIdGetter<Long>> {

	
	/**
	 * Metodo encargado de realizar el cambio el cambio de variable del front con el back de las entidades para que no lancen excepciones de mala construcion de la query<br>
	 * Este metodo debe de ser sobrecargado si existe como ejemplo una relacion en la entidad<br>
	 * Ejemplo: si la entidad Cliente tiene un Producto: desde el front se enviaria productoId y en el back se haria una sobrecarga para cambiarlo a producto.id
	 * @param s que viene del lado del front-end
	 * @return String que contiene una propiedad de la entidad a procesar
	 */
	protected String replaceAlias(String s) {
		return s;
	}

	/**
	 * Metodo que sacar las distintos wheres para realizar la consulta jpa
	 * @param filter que contiene los parametros para realizar la construcion del Specification
	 * @return Specification de la entidad
	 * @see GenericSpec
	 * @see GenericSpecBuilder
	 */
	protected Specification<T> generateSpecification(String filter) {
		GenericSpecBuilder<T> builder = new GenericSpecBuilder<T>();

		Pattern pattern = Pattern.compile(GenericSpec.PATTERN,Pattern.UNICODE_CHARACTER_CLASS);
		Matcher matcher = pattern.matcher(filter + ",");

		while (matcher.find()) {
			builder.with(replaceAlias(matcher.group(GenericSpec.MATCHER_GROUP_KEY)),
					matcher.group(GenericSpec.MATCHER_GROUP_OPERATION), matcher.group(GenericSpec.MATCHER_GROUP_VALUE));
		}

		return builder.build();
	}
	
}