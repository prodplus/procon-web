package br.com.procon.utils;

import java.util.List;
import java.util.stream.Collectors;

import br.com.procon.models.Processo;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class GeradorAutos {

	/**
	 * Gera a string para o número dos autos.
	 * 
	 * @param processos
	 * @param ano
	 * @return
	 */
	public static String getAutos(List<Processo> processos, Integer ano) {
		List<Processo> processosAno = processos.stream().filter(p -> p.getData().getYear() == ano)
				.collect(Collectors.toList());

		StringBuilder builder = new StringBuilder();
		Integer contador = 0;

		if (processosAno != null && !processosAno.isEmpty()) {
			for (Processo proc : processosAno) {
				Integer tmp = Integer.valueOf(proc.getAutos().split("/")[0]);
				if (tmp > contador)
					contador = tmp;
			}

			builder.append(String.format("%03d/%04d", contador + 1, ano));
		} else {
			builder.append(String.format("%03d/%04d", 1, ano));
		}

		return builder.toString();
	}

}
