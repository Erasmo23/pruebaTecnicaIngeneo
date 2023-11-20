package co.com.ingeneo.api.config.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import co.com.ingeneo.api.config.CommonConfigurationUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiErrorResponse {

	private Integer statusCode;

	private String type;

	@Builder.Default
	private String source = "base";

	private String message;

	private String description;

	@JsonFormat(pattern = CommonConfigurationUtils.DATE_TIME_FORMAT)
	private LocalDateTime localDateTime;

}