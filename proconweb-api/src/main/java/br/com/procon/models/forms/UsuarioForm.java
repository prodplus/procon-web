package br.com.procon.models.forms;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.procon.models.Usuario;
import br.com.procon.repositories.PerfilRepository;
import br.com.procon.repositories.UsuarioRepository;
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

	public Usuario converter(UsuarioRepository usuarioRepository,
			PerfilRepository perfilRepository) {
		if (this.id != null) {
			return usuarioRepository.findById(this.id).map(u -> {
				u.setNome(this.getNome());
				u.setEmail(this.getEmail());
				u.setPassword(this.getPassword());
				u.setPerfil(perfilRepository.findByRole(this.perfil).get());
				return u;
			}).orElse(new Usuario(this.getId(), this.getNome(), this.getEmail(), this.getPassword(),
					perfilRepository.findByRole(this.getPerfil()).get(), true));
		} else {
			return new Usuario(this.getId(), this.getNome(), this.getEmail(), this.getPassword(),
					perfilRepository.findByRole(this.getPerfil()).get(), true);
		}
	}

}
