package br.com.procon.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import br.com.procon.models.auxiliares.Endereco;
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
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = -892373347989897798L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	private String fantasia;
	private String razaoSocial;
	@Column(length = 20)
	private String cnpj;
	private String email;
	@Embedded
	private Endereco endereco;
	@ElementCollection
	@CollectionTable(name = "fones_f", joinColumns = @JoinColumn(name = "forn_id"))
	@Column(name = "fone", length = 20)
	private Set<String> fones = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fantasia == null) ? 0 : fantasia.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (fantasia == null) {
			if (other.fantasia != null)
				return false;
		} else if (!fantasia.equals(other.fantasia))
			return false;
		return true;
	}

}
