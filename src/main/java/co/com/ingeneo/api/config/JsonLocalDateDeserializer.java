package co.com.ingeneo.api.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class JsonLocalDateDeserializer extends StdDeserializer<LocalDate> {

	private static final long serialVersionUID = 8510184093545987068L;

	protected JsonLocalDateDeserializer() {
		super(LocalDate.class);
	}
	
	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		 return LocalDate.parse(p.readValueAs(String.class), DateTimeFormatter.ofPattern(CommonConfigurationUtils.DATE_FORMAT));
	}
	
}