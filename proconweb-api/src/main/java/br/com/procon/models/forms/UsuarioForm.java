package br.com.procon.models.forms;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class UsuarioForm implements Serializable {

	private static final long serialVersionUID = -5579352740629660827L;

	private Integer id;
	@NotBlank(message = "o nome é obrigatório!")
	private String nome;
	@NotBlank(message = "o email é obrigatório!")
	@Email(message = "email inválido!")
	private String email;
	@NotBlank(message = "a senha é obrigatória!")
	@Size(max = 10, message = "a senha deve possuir no máximo 10 caracteres!")
	private String password;
	@NotBlank(message = "o perfil é obrigatório!")
	private String perfil;

}
