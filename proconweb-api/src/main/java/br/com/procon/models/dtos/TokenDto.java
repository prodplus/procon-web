package br.com.procon.models.dtos;

import java.io.Serializable;

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
public class TokenDto implements Serializable {

	private static final long serialVersionUID = 1560919996419447239L;
	
	private String token;
	private String tipo;

}
