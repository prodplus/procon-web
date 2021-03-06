package br.com.procon.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.procon.models.Fornecedor;
import br.com.procon.models.auxiliares.FornecedorNro;
import br.com.procon.models.dtos.AtendimentoDto;
import br.com.procon.models.enums.TipoLog;
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
	@Autowired
	private LogService logService;

	public AtendimentoDto salvar(@Valid AtendimentoForm atendimento) {
		try {
			Atendimento ate = this.atendimentoRepository.save(atendimento.converter(
					this.consumidorService, this.fornecedorService, this.usuarioService));
			this.logService.salvar(LocalDateTime.now(), "Atendimento " + ate.getId(),
					TipoLog.INSERCAO);
			return new AtendimentoDto(ate);
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
				Atendimento ate = this.atendimentoRepository.save(novo);
				this.logService.salvar(LocalDateTime.now(), "Atendimento " + ate.getId(),
						TipoLog.ATUALIZACAO);
				return new AtendimentoDto(ate);
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
			Atendimento ate = this.atendimentoRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			this.logService.salvar(LocalDateTime.now(), "Atendimento " + ate.getId(),
					TipoLog.EXCLUSAO);
			this.atendimentoRepository.delete(ate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Long atendimentosAno() {
		return this.atendimentoRepository
				.countByDataAfter(LocalDate.of(LocalDate.now().getYear(), 1, 1));
	}

	public List<FornecedorNro> ranking(Integer ano) {
		try {
			List<Fornecedor> fornecedores = this.fornecedorService.listar();
			List<FornecedorNro> fornsNro = new ArrayList<>();
			List<Atendimento> ateAno = this.atendimentoRepository
					.findAllByDataBetween(LocalDate.of(ano, 1, 1), LocalDate.of(ano + 1, 1, 1));
			fornecedores.forEach(f -> fornsNro.add(new FornecedorNro(f, 0)));
			for (Atendimento ate : ateAno) {
				for (Fornecedor forn : ate.getFornecedores()) {
					int index = fornsNro.indexOf(new FornecedorNro(forn, 0));
					FornecedorNro fornNro = fornsNro.get(index);
					fornNro.setProcessos(fornNro.getProcessos() + 1);
					fornsNro.set(index, fornNro);
				}
			}
			FornecedorNro outros = new FornecedorNro(
					new Fornecedor(null, "outros", "outros", "", "", null, null), 0);
			fornsNro.forEach(f -> {
				if (f.getProcessos() == 1)
					outros.setProcessos(outros.getProcessos() + 1);
			});
			List<FornecedorNro> fornsNro2 = fornsNro.stream().filter(f -> f.getProcessos() > 1)
					.collect(Collectors.toList());
			Collections.sort(fornsNro2);
			fornsNro2.add(outros);
			return fornsNro2;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
