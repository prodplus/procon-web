package br.com.procon.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Atendimento;
import br.com.procon.models.dtos.AtendimentoDto;
import br.com.procon.models.forms.AtendimentoForm;
import br.com.procon.repositories.AtendimentoRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class AtendimentoService {

	@Autowired
	private AtendimentoRepository atendimentoRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ConsumidorService consumidorService;
	@Autowired
	private FornecedorService fornecedorService;

	public AtendimentoDto salvar(@Valid AtendimentoForm atendimento) {
		try {
			return new AtendimentoDto(this.atendimentoRepository.save(atendimento.converter(
					this.consumidorService, this.fornecedorService, this.usuarioService)));
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public AtendimentoDto atualizar(Integer id, @Valid AtendimentoForm atendimento) {
		try {
			return this.atendimentoRepository.findById(id).map(novo -> {
				Atendimento i = atendimento.converter(this.consumidorService,
						this.fornecedorService, this.usuarioService);
				novo.setConsumidores(i.getConsumidores());
				novo.setFornecedores(i.getFornecedores());
				novo.setData(i.getData());
				novo.setRelato(i.getRelato());
				novo.setAtendente(i.getAtendente());
				return new AtendimentoDto(this.atendimentoRepository.save(i));
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "atendimento não localizado!",
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

	public Atendimento buscar(Integer id) {
		try {
			return this.atendimentoRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "atendimento não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<AtendimentoDto> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			Page<Atendimento> page = this.atendimentoRepository.findAll(pageable);
			List<AtendimentoDto> lista = new ArrayList<>();
			page.getContent().forEach(a -> lista.add(new AtendimentoDto(a)));
			return new PageImpl<>(lista, pageable, page.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<AtendimentoDto> listar(String parametro, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			List<AtendimentoDto> lista = new ArrayList<>();
			Page<Atendimento> page = this.atendimentoRepository
					.findAllByConsumidoresDenominacaoContainingIgnoreCaseOrFornecedoresFantasiaContainingIgnoreCaseOrFornecedoresRazaoSocialContainingIgnoreCase(
							parametro, parametro, parametro, pageable);
			page.getContent().forEach(a -> lista.add(new AtendimentoDto(a)));
			return new PageImpl<>(lista, pageable, page.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.atendimentoRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
