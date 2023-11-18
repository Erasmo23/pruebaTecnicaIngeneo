package co.com.ingeneo.api.config;

import java.util.function.Function;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public abstract class CommonConfigurationUtils {

	public CommonConfigurationUtils() {
		throw new IllegalStateException("Constants class must not be instantiated!");
	}
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_AM_PM = "dd/MM/yyyy hh:mm:ss a";
    
    
    public static final Sort generateSort(final String orderByExpresion,final Function<String, String> funcion) {
		Sort sort = null;

		if (orderByExpresion != null && !orderByExpresion.isEmpty()) {
			String[] sortArray = orderByExpresion.split(",");
			String path = funcion.apply(sortArray[0]);
			Direction direction = sortArray.length == 1 ? Direction.ASC : Direction.fromString(sortArray[1]);
			sort = Sort.by(direction, path);
		}

		return sort;
	}
    
    public static final Pageable generatePageable(final Integer offset, final Integer maxResults, final Sort sort) {
		return sort != null ? PageRequest.of(offset, maxResults, sort) : PageRequest.of(offset, maxResults);
	}
	
}