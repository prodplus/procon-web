package br.com.procon.utils;

import java.util.Collections;
import java.util.List;

import br.com.procon.models.dtos.ProcessoDto;

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

	/**
	 * Gera a string com o número dos autos.
	 * 
	 * @param processosAno Lista de Processos do ano.
	 * @param ano          Ano do processo.
	 * @return
	 */
	public static String getAutos(List<ProcessoDto> processosAno, Integer ano) {
		if (processosAno != null && !processosAno.isEmpty()) {
			Collections.sort(processosAno);
			Collections.reverse(processosAno);
			int nro = processosAno.get(0).getOrdem() + 1;
			return String.format("%03d/%04d", nro, ano);
		} else {
			return String.format("%03d/%04d", 1, ano);
		}
	}

}
