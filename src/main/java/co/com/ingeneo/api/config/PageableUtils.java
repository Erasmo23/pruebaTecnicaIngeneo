package co.com.ingeneo.api.config;

import java.util.function.Function;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageableUtils {

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