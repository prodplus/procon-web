package br.com.procon.models.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.procon.models.Processo;
import br.com.procon.models.enums.Situacao;
import br.com.procon.models.enums.TipoProcesso;
import br.com.procon.utils.AutosUtils;
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
public class ProcessoDto implements Serializable, Comparable<ProcessoDto> {

	private static final long serialVersionUID = 8597702419389422862L;
	
	private Integer id;
	private TipoProcesso tipo;
	private String autos;
	private int ordem;
	private List<String> consumidores = new ArrayList<>();
	private List<String> fornecedores = new ArrayList<>();
	private LocalDate data;
	private Situacao situacao;
	
	public ProcessoDto(Processo processo) {
		this.setId(processo.getId());
		this.setTipo(processo.getTipo());
		this.setAutos(processo.getAutos());
		this.setOrdem(AutosUtils.getNroAutos(processo.getAutos()));
		processo.getConsumidores().forEach(c -> this.consumidores.add(c.getDenominacao()));
		processo.getFornecedores().forEach(f -> this.fornecedores.add(f.getFantasia()));
		this.setData(processo.getData());
		this.setSituacao(processo.getSituacao());
	}

	@Override
	public int compareTo(ProcessoDto o) {
		if (this.ordem == o.getOrdem())
			if (this.data != null && o.getData() != null)
				return this.data.compareTo(o.getData()) * -1;
			else 
				return 0;
		else
			return o.getOrdem() - this.ordem;
	}

}
