package co.com.ingeneo.api.repository.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import co.com.ingeneo.api.repository.domain.HasIdGetter;

/**
 * To support multiple filtering it creates a list of criteria specification.
 *
 * @param <T>
 *     an entity
 */
public class GenericSpecBuilder <T extends HasIdGetter<Long>> {

	private List<SearchCriteria> params;

    public GenericSpecBuilder() {
        params = new ArrayList<>();
    }

    /**
     * Clear previous filters.
     */
    public void clearSearchCriteria() {
        params = new ArrayList<>();
    }

    public GenericSpecBuilder<T> with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<T> build() {
        return params.stream()
            .map(this::instantiate)
            .reduce((spec, spec2) -> Specification.where(spec).and(spec2))
            .orElse(instantiate(new SearchCriteria("", "", "")));
    }

    protected Specification<T> instantiate(SearchCriteria criteria) {
        return new GenericSpec<>(criteria);
    }
	
}