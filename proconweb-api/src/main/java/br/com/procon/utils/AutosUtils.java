package br.com.procon.utils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class AutosUtils {

	/**
	 * Obtém um valor numérico para a string dos autos.
	 * 
	 * @param autos
	 * @return
	 */
	public static int getNroAutos(String autos) {
		String[] parts = autos.split("/");
		int nro = Integer.parseInt(parts[0]);
		return nro;
	}

}
