package br.com.procon.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.procon.models.auxiliares.ItemFiscalizacao;
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
public class Fiscalizacao implements Serializable {

	private static final long serialVersionUID = -1104043666879314177L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	@NotNull(message = "a data é obrigatória!")
	private LocalDateTime data;
	@ManyToOne
	@JoinColumn(nullable = false)
	@NotNull(message = "o fornecedor é obrigatório!")
	private Fornecedor fornecedor;
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String observacoes;
	@ManyToOne
	@JoinColumn(nullable = false)
	@NotNull(message = "o setor é obrigatório!")
	private SetorFiscalizacao setor;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<ItemFiscalizacao> itens = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Fiscalizacao other = (Fiscalizacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
