package co.com.ingeneo.api.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.jpa.domain.Specification;

import co.com.ingeneo.api.repository.domain.HasIdGetter;
import co.com.ingeneo.api.repository.filter.GenericSpec;
import co.com.ingeneo.api.repository.filter.GenericSpecBuilder;

public class GenericSpecificationGenerator <T extends HasIdGetter<Long>> {

	protected String replaceAlias(String s) {
		return s;
	}

	protected Specification<T> generateSpecification(String filter) {
		GenericSpecBuilder<T> builder = new GenericSpecBuilder<T>();

		Pattern pattern = Pattern.compile(GenericSpec.PATTERN);
		Matcher matcher = pattern.matcher(filter + ",");

		while (matcher.find()) {
			builder.with(replaceAlias(matcher.group(GenericSpec.MATCHER_GROUP_KEY)),
					matcher.group(GenericSpec.MATCHER_GROUP_OPERATION), matcher.group(GenericSpec.MATCHER_GROUP_VALUE));
		}

		return builder.build();
	}
	
}