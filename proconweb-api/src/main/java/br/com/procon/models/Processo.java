package br.com.procon.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.procon.models.auxiliares.Movimento;
import br.com.procon.models.enums.Situacao;
import br.com.procon.models.enums.TipoProcesso;
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
@Entity
public class Processo implements Serializable {

	private static final long serialVersionUID = 5443898870084380560L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoProcesso tipo;
	@Column(nullable = false, unique = true, length = 10)
	private String autos;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Consumidor> consumidores = new ArrayList<>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Consumidor> representantes = new ArrayList<>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Fornecedor> fornecedores = new ArrayList<>();
	@Column(nullable = false)
	private LocalDate data;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Movimento> movimentacao = new ArrayList<>();
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String relato;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Situacao situacao;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autos == null) ? 0 : autos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		if (autos == null) {
			if (other.autos != null)
				return false;
		} else if (!autos.equals(other.autos))
			return false;
		return true;
	}

}
