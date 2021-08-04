package br.com.procon.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Perfil;
import br.com.procon.models.Usuario;
import br.com.procon.models.dtos.UsuarioDto;
import br.com.procon.models.forms.UsuarioForm;
import br.com.procon.repositories.PerfilRepository;
import br.com.procon.repositories.UsuarioRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PerfilRepository perfilRepository;

	public UsuarioDto salvar(@Valid UsuarioForm usuario) {
		try {
			Usuario novo = usuario.converter(this.usuarioRepository, this.perfilRepository);
			UsuarioDto u = new UsuarioDto(this.usuarioRepository.save(novo));
			return u;
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "usuário já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public UsuarioDto atualizar(Integer id, @Valid UsuarioForm usuario) {
		try {
			return this.usuarioRepository.findById(id).map(novo -> {
				novo.setEmail(usuario.getEmail());
				novo.setNome(usuario.getNome());
				novo.setPerfil(this.perfilRepository.findByRole(usuario.getPerfil()).get());
				if (usuario.getPassword() != null && usuario.getPassword().length() > 0)
					novo.setPassword(usuario.getPassword());
				return new UsuarioDto(this.usuarioRepository.save(novo));
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "usuário já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public UsuarioDto buscar(Integer id) {
		try {
			return this.usuarioRepository.findById(id).map(u -> new UsuarioDto(u))
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Usuario buscarI(Integer id) {
		try {
			return this.usuarioRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<UsuarioDto> listar(boolean ativos, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "nome");
			Page<Usuario> page = this.usuarioRepository.findAllByAtivo(ativos, pageable);
			List<UsuarioDto> lista = new ArrayList<>();
			page.getContent().forEach(u -> lista.add(new UsuarioDto(u)));
			return new PageImpl<>(lista, pageable, page.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void ativar(Integer id, boolean ativar) {
		try {
			this.usuarioRepository.findById(id).map(n -> {
				n.setAtivo(ativar);
				return this.usuarioRepository.save(n);
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.usuarioRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"não é possível excluir o usuário!", e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Perfil> getPerfis() {
		try {
			return this.perfilRepository.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}
	
	public boolean loginExiste(String email) {
		return this.usuarioRepository.existsByEmail(email);
	}

}
