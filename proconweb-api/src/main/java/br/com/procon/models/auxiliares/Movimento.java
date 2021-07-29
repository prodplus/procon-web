package br.com.procon.models.auxiliares;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.procon.models.enums.Situacao;
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
public class Movimento implements Serializable {

	private static final long serialVersionUID = -714242681743161646L;
	@Column(nullable = false)
	private LocalDate data;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Situacao de;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Situacao para;
	private String averbacao;
	private LocalDate auxD;
	private LocalTime auxT;

}
