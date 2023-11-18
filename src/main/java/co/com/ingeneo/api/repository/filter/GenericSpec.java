package co.com.ingeneo.api.repository.filter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import co.com.ingeneo.api.config.CommonConfigurationUtils;
import co.com.ingeneo.api.repository.domain.HasIdGetter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Creates the criteria specification.
 *
 * @param <T>
 *     an entity
 * @param <I>
 *     the id type of the entity
 */
public class GenericSpec<T extends HasIdGetter<I>, I extends Serializable> implements Specification<T> {

	private static final long serialVersionUID = 7168029442902828393L;

	public static final String PATTERN = "(\\w+?)(:|<|>|-in-|-bw-)([A-Za-z0-9_:.\\s\\+-]+?),";
    
    public static final Integer MATCHER_GROUP_KEY = 1;
    
    public static final Integer MATCHER_GROUP_OPERATION = 2;
    
    public static final Integer MATCHER_GROUP_VALUE = 3;

    protected SearchCriteria criteria;

    public GenericSpec(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    	
    	if (criteria.getKey().contains(".")) {
    		return getJoinPredicate(criteriaBuilder, root);
    	}

        if (">".equalsIgnoreCase(criteria.getOperation())) {
            return criteriaBuilder.greaterThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue().toString()
            );
        } else if ("<".equalsIgnoreCase(criteria.getOperation())) {
            return criteriaBuilder.lessThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue().toString()
            );
        } else if (":".equalsIgnoreCase(criteria.getOperation())) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"
                );
            } else if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                return criteriaBuilder.equal(root.get(criteria.getKey()), toLocalDateTime(criteria.getValue().toString()));
            } else if (root.get(criteria.getKey()).getJavaType() == LocalDate.class) {
                return criteriaBuilder.equal(root.get(criteria.getKey()), toLocalDate(criteria.getValue().toString()));
            } else if (root.get(criteria.getKey()).getJavaType() == Boolean.class
                || root.get(criteria.getKey()).getJavaType() == boolean.class) {
                return criteriaBuilder.equal(root.get(criteria.getKey()), Boolean.parseBoolean(criteria.getValue().toString()));
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if ("-bw-".equalsIgnoreCase(criteria.getOperation())) {
            String[] datesArray = getDatesArray();
            if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                return criteriaBuilder.between(root.get(criteria.getKey()), toLocalDateTime(datesArray[0]), toLocalDateTime(datesArray[1]));
            }
            if (root.get(criteria.getKey()).getJavaType() == LocalDate.class) {
                return criteriaBuilder.between(root.get(criteria.getKey()), toLocalDate(datesArray[0]), toLocalDate(datesArray[1]));
            }
        } else if ("-in-".equalsIgnoreCase(criteria.getOperation())) {
            if (root.get(criteria.getKey()).getJavaType() == Integer.class) {
                return getInPredicate(criteriaBuilder, root.get(criteria.getKey()), getIntValuesList());
            }
            return getInPredicate(criteriaBuilder, root.get(criteria.getKey()), getValuesList());
        } else {
            return null;
        }

        return null;
    }
    
    private Predicate getJoinPredicate(final CriteriaBuilder criteriaBuilder,final Root<?> root ) {
    	String[] arrayString = criteria.getKey().split("\\.");
    	
    	if (arrayString.length == 2) {
    		return root.join(arrayString[0]).get(arrayString[1]).in(getIntValuesList());
    	}else if (arrayString.length == 3) {
    		return root.join(arrayString[0]).join(arrayString[1]).get(arrayString[2]).in(getIntValuesList());
    	}
    	
    	return null;
    }

    /**
     * Due to database limitations, we divide the list of values into multiple "in" conditions with a maximum of 1000 values.
     *
     * @param criteriaBuilder
     *     the criteria builder.
     * @param propertyPath
     *     the property to apply the in statement.
     *
     * @return the predicate.
     */
    protected Predicate getInPredicate(final CriteriaBuilder criteriaBuilder, final Path<?> propertyPath, final List<?> valuesList) {
    	/*List<List<?>> loanIdsGroups = OracleLimitHandler.getInStatementGroups(valuesList);
        Predicate predicateGroupOr = null;
        for (int i = 0; i < loanIdsGroups.size(); i++) {
            if (predicateGroupOr == null) {
                predicateGroupOr = criteriaBuilder.or(propertyPath.in(loanIdsGroups.get(i)));
            } else {
                predicateGroupOr = criteriaBuilder.or(predicateGroupOr, propertyPath.in(loanIdsGroups.get(i)));
            }
        }*/

        return criteriaBuilder.and(propertyPath.in(valuesList));
    }

    private String[] getDatesArray() {
        String[] datesArray = criteria.getValue().toString().split("\\+");
        if (datesArray.length <= 1) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "missing dates for between filter");
        }
        return datesArray;
    }

    private LocalDateTime toLocalDateTime(final String dateTimeAsString) {
        return LocalDateTime.parse(dateTimeAsString, DateTimeFormatter.ofPattern(CommonConfigurationUtils.DATE_TIME_FORMAT));
    }

    private LocalDate toLocalDate(final String dateAsString) {
        return LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern(CommonConfigurationUtils.DATE_FORMAT));
    }

    protected List<String> getValuesList() {
        return List.of(criteria.getValue().toString().split("\\+"));
    }

    protected List<Integer> getIntValuesList() {
        List<Integer> values = new ArrayList<>();
        for (String value : getValuesList()) {
            values.add(Integer.parseInt(value.trim()));
        }
        return values;
    }

    protected boolean isNullPredicate(final Predicate predicate) {
        return predicate == null;
    }
	
}