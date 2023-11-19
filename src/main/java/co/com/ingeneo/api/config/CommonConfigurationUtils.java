package co.com.ingeneo.api.config;

import java.security.SecureRandom;

public abstract class CommonConfigurationUtils {

	public CommonConfigurationUtils() {
		throw new IllegalStateException("Constants class must not be instantiated!");
	}
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_AM_PM = "dd/MM/yyyy hh:mm:ss a";
    
    public static final char[] CARACTERES_ALFANUMERICOS_DISPONIBLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
    
    
    /**
     * Metodo que genera un string de "n" longitud aleatorio con cada uno de los caracteres alfanumericos establecidos en la propiedad static de la clase
     * @param longitud Cantidad de caracteres que seran generados aleatorios
     * @return String que contiene cierta cantidad de caracteres alfanumericos
     */
    public static String generarStringAlfanumericoAleatorio(int longitud) {
        StringBuilder builder = new StringBuilder(longitud);
        SecureRandom random = new SecureRandom();

        random.ints(longitud, 0, (CARACTERES_ALFANUMERICOS_DISPONIBLES.length - 1))
                .mapToObj(valor -> CARACTERES_ALFANUMERICOS_DISPONIBLES[valor])
                .forEach(builder::append);

        return builder.toString();
    }
    
}