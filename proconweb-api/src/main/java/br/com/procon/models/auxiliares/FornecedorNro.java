package br.com.procon.models.auxiliares;

import java.io.Serializable;

import br.com.procon.models.Fornecedor;
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
public class FornecedorNro implements Serializable, Comparable<FornecedorNro> {

	private static final long serialVersionUID = -5477599842443711857L;

	private Fornecedor fornecedor;
	private Integer processos;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
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
		FornecedorNro other = (FornecedorNro) obj;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		return true;
	}

	@Override
	public int compareTo(FornecedorNro o) {
		if (this.processos != null && o.getProcessos() != null)
			return this.processos.compareTo(o.getProcessos()) * -1;
		return 0;
	}

}
