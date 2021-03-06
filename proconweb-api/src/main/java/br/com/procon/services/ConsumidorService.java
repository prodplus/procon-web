package br.com.procon.services;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Consumidor;
import br.com.procon.models.enums.TipoLog;
import br.com.procon.repositories.ConsumidorRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class ConsumidorService {

	@Autowired
	private ConsumidorRepository consumidorRepository;
	@Autowired
	private LogService logService;

	public Consumidor salvar(@Valid Consumidor consumidor) {
		try {
			consumidor.setDenominacao(consumidor.getDenominacao().toUpperCase().trim());
			Consumidor cons = this.consumidorRepository.save(consumidor);
			this.logService.salvar(LocalDateTime.now(), "Consumidor " + cons.getDenominacao(),
					TipoLog.INSERCAO);
			return cons;
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "consumidor já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Consumidor atualizar(Integer id, @Valid Consumidor consumidor) {
		try {
			return this.consumidorRepository.findById(id).map(novo -> {
				novo.setCadastro(consumidor.getCadastro());
				novo.setDenominacao(consumidor.getDenominacao().toUpperCase().trim());
				novo.setEmail(consumidor.getEmail());
				novo.setEndereco(consumidor.getEndereco());
				novo.setFones(consumidor.getFones());
				novo.setTipo(consumidor.getTipo());
				this.logService.salvar(LocalDateTime.now(), "Consumidor " + novo.getDenominacao(),
						TipoLog.ATUALIZACAO);
				return this.consumidorRepository.save(novo);
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "consumidor não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "consumidor já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Consumidor buscar(Integer id) {
		try {
			return this.consumidorRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "consumidor não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Consumidor buscar(String cadastro) {
		try {
			return this.consumidorRepository.findByCadastro(cadastro)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "consumidor não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<Consumidor> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "denominacao");
			return this.consumidorRepository.findAll(pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<Consumidor> listar(String parametro, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "denominacao");
			if (Character.isDigit(parametro.charAt(0)))
				return this.consumidorRepository.findAllByCadastroContaining(parametro, pageable);
			else
				return this.consumidorRepository.findAllByDenominacaoContainingIgnoreCase(parametro,
						pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			Consumidor cons = this.consumidorRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			this.logService.salvar(LocalDateTime.now(), "Consumidor " + cons.getDenominacao(),
					TipoLog.EXCLUSAO);
			this.consumidorRepository.delete(cons);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "consumidor não localizado!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"o consumidor não pode ser excluído!", e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public boolean consumidorExiste(String cadastro) {
		try {
			return this.consumidorRepository.existsByCadastro(cadastro);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
