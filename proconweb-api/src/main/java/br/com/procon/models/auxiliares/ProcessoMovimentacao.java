package br.com.procon.models.auxiliares;

import java.io.Serializable;

import br.com.procon.models.dtos.ProcessoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ProcessoMovimentacao implements Serializable, Comparable<ProcessoMovimentacao> {

	private static final long serialVersionUID = -1920396547011231827L;

	private ProcessoDto processo;
	private Movimento movimento;

	@Override
	public int compareTo(ProcessoMovimentacao o) {
		if (this.movimento.getData() != null && o.getMovimento().getData() != null)
			return this.movimento.getData().compareTo(o.getMovimento().getData());
		return 0;
	}

}
