package br.com.procon.models.auxiliares;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.procon.models.Dispositivo;
import br.com.procon.models.enums.Cumprimento;
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
public class DispoCump implements Serializable {

	private static final long serialVersionUID = -6914830393440900572L;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Dispositivo dispositivo;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Cumprimento cumprimento;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dispositivo == null) ? 0 : dispositivo.hashCode());
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
		DispoCump other = (DispoCump) obj;
		if (dispositivo == null) {
			if (other.dispositivo != null)
				return false;
		} else if (!dispositivo.equals(other.dispositivo))
			return false;
		return true;
	}

}
