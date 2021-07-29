package br.com.procon.models.auxiliares;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.procon.models.enums.UF;
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
@Embeddable
public class Endereco implements Serializable, Comparable<Endereco> {

	private static final long serialVersionUID = -1215304834350865024L;
	@Column(length = 30)
	private String cep;
	private String logradouro;
	@Column(length = 30)
	private String numero;
	@Column(length = 50)
	private String complemento;
	private String bairro;
	private String municipio;
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private UF uf;

	@Override
	public int compareTo(Endereco o) {
		if (this.municipio != null && o.getMunicipio() != null)
			if (this.municipio.compareTo(o.getMunicipio()) == 0)
				if (this.logradouro != null && o.getLogradouro() != null)
					if (this.logradouro.compareTo(o.getLogradouro()) == 0)
						if (this.numero != null && o.getNumero() != null)
							if (this.numero.compareTo(o.getNumero()) == 0)
								if (this.complemento != null && o.getComplemento() != null)
									return this.complemento.compareTo(o.getComplemento());
								else
									return 0;
							else
								return this.numero.compareTo(o.getNumero());
						else
							return 0;
					else
						return this.logradouro.compareTo(o.getLogradouro());
				else
					return 0;
			else
				return this.municipio.compareTo(o.getMunicipio());
		else
			return 0;
	}

}
