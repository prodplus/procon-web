package br.com.procon.models.dtos;

import java.io.Serializable;

import br.com.procon.models.Processo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcDesc implements Serializable {

	private static final long serialVersionUID = -3143151921006179772L;
	
	private ProcessoDto processo;
	private String descricao;
	
	public ProcDesc(Processo processo, String descricao) {
		this.setProcesso(new ProcessoDto(processo));
		this.setDescricao(descricao);
	}

}
