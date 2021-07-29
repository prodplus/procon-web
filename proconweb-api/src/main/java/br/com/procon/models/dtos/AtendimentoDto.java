package br.com.procon.models.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.procon.models.Atendimento;
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
public class AtendimentoDto implements Serializable {

	private static final long serialVersionUID = -8113599840512729148L;
	
	private Integer id;
	private List<String> consumidores = new ArrayList<>();
	private List<String> fornecedores = new ArrayList<>();
	private LocalDate data;
	private String relato;
	private String atendente;
	
	public AtendimentoDto(Atendimento atendimento) {
		this.setId(atendimento.getId());
		atendimento.getConsumidores().forEach(c -> this.consumidores.add(c.getDenominacao()));
		atendimento.getFornecedores().forEach(f -> this.fornecedores.add(f.getFantasia()));
		this.setData(atendimento.getData());
		this.setRelato(atendimento.getRelato());
		this.setAtendente(atendimento.getAtendente().getNome());
	}

}
