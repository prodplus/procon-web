package br.com.procon.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class MascarasUtils {

	/**
	 * Aplica máscara a uma string.
	 * 
	 * @param pattern
	 * @param value
	 * @return
	 */
	public static String format(String pattern, String value) {
		MaskFormatter mask;
		try {
			String tratada = value.replaceAll("[-()]*", "");
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(tratada);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static String foneFormat(String value) {
		MaskFormatter mask;
		try {
			if (value.charAt(2) == '9')
				mask = new MaskFormatter("(##) #####-####");
			else
				mask = new MaskFormatter("(##) ####-####");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
