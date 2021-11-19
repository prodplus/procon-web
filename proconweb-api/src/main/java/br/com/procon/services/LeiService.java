package br.com.procon.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Dispositivo;
import br.com.procon.models.Lei;
import br.com.procon.repositories.DispositivoRepository;
import br.com.procon.repositories.LeiRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class LeiService {

	@Autowired
	private LeiRepository leiRepository;
	@Autowired
	private DispositivoRepository dispositivoRepository;

	public Lei salvar(@Valid Lei lei) {
		try {
			return this.leiRepository.save(lei);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "lei já cadastrada!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Lei atualizar(Integer id, @Valid Lei lei) {
		try {
			return this.leiRepository.findById(id).map(nova -> {
				nova.setDescricao(lei.getDescricao());
				nova.setSumula(lei.getSumula());
				return this.leiRepository.save(nova);
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lei não localizada!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "lei já cadastrada!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Lei buscar(Integer id) {
		try {
			return this.leiRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lei não localizada!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Lei> listar() {
		try {
			return this.leiRepository.findAll(Sort.by(Direction.ASC, "descricao"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<Lei> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "descricao");
			return this.leiRepository.findAll(pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Lei> listar(String descricao) {
		try {
			return this.leiRepository.findAllByDescricaoContainingIgnoreCase(descricao);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}
	
	public Page<Lei> listar(int pagina, int quant, String descricao) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "descricao");
			return this.leiRepository.findAllByDescricaoContainingIgnoreCase(descricao, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.leiRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "não é possível excluir a lei!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}
	
	public Dispositivo salvar(@Valid Dispositivo dispositivo) {
		try {
			return this.dispositivoRepository.save(dispositivo);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "lei já cadastrada!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}
	
	public void excluirDispo(Integer id) {
		try {
			this.dispositivoRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}
	
	public List<Dispositivo> listarDispositivos(Integer idLei) {
		try {
			return this.dispositivoRepository.findAllByLeiId(idLei);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
