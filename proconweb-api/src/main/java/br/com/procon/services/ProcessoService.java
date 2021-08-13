package br.com.procon.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Fornecedor;
import br.com.procon.models.Processo;
import br.com.procon.models.auxiliares.FornecedorNro;
import br.com.procon.models.auxiliares.Movimento;
import br.com.procon.models.dtos.ProcessoDto;
import br.com.procon.models.enums.Situacao;
import br.com.procon.models.forms.ProcessoForm;
import br.com.procon.repositories.ProcessoRepository;
import br.com.procon.utils.GeradorAutos;
import br.com.procon.utils.ProcessoUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository processoRepository;
	@Autowired
	private ConsumidorService consumidorService;
	@Autowired
	private FornecedorService fornecedorService;
	@Autowired
	private UsuarioService usuarioService;

	public ProcessoDto salvar(@Valid ProcessoForm processo) {
		try {
			if (processo.getAutos() == null || processo.getAutos().isBlank())
				processo.setAutos(GeradorAutos.getAutos(
						this.processoRepository.findAllByDataBetween(
								LocalDate.of(processo.getData().getYear(), 1, 1),
								LocalDate.of(processo.getData().getYear() + 1, 1, 1)),
						processo.getData().getYear()));
			if (processo.getMovimentacao() == null || processo.getMovimentacao().isEmpty())
				processo.getMovimentacao().add(new Movimento(processo.getData(), Situacao.BALCAO,
						Situacao.AUTUADO, "recém autuado", null, null));
			processo.setSituacao(ProcessoUtils.handleSituacao(processo.getMovimentacao()));
			return new ProcessoDto(this.processoRepository.save(processo.converter(
					this.consumidorService, this.fornecedorService, this.usuarioService)));
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "processo já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public ProcessoDto atualizar(Integer id, @Valid ProcessoForm processo) {
		try {
			return this.processoRepository.findById(id).map(novo -> {
				Processo proc = processo.converter(this.consumidorService, this.fornecedorService,
						this.usuarioService);
				novo.setAutos(proc.getAutos());
				novo.setData(proc.getData());
				novo.setConsumidores(proc.getConsumidores());
				novo.setRepresentantes(proc.getRepresentantes());
				novo.setFornecedores(proc.getFornecedores());
				novo.setRelato(proc.getRelato());
				novo.setMovimentacao(proc.getMovimentacao());
				if (novo.getMovimentacao() == null || novo.getMovimentacao().isEmpty())
					novo.getMovimentacao().add(new Movimento(novo.getData(), Situacao.BALCAO,
							Situacao.AUTUADO, "recém autuado", null, null));
				novo.setSituacao(ProcessoUtils.handleSituacao(novo.getMovimentacao()));
				return new ProcessoDto(this.processoRepository.save(novo));
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "processo não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "processo já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Processo buscar(Integer id) {
		try {
			return this.processoRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "processo não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			Page<Processo> page = this.processoRepository.findAll(pageable);
			return transformaDto(pageable, page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private static Page<ProcessoDto> transformaDto(Pageable pageable, Page<Processo> page) {
		List<ProcessoDto> dtos = new ArrayList<>();
		page.getContent().forEach(p -> dtos.add(new ProcessoDto(p)));
		Collections.sort(dtos);
		return new PageImpl<>(dtos, pageable, page.getTotalElements());
	}

	public static List<ProcessoDto> transformaDto(List<Processo> processos) {
		List<ProcessoDto> dtos = new ArrayList<>();
		processos.forEach(p -> dtos.add(new ProcessoDto(p)));
		return dtos;
	}

	public Page<ProcessoDto> listarPorAutos(String autos, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			Page<Processo> page = this.processoRepository.findAllByAutosContaining(autos, pageable);
			return transformaDto(pageable, page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listarPorConsumidor(String parametro, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			Page<Processo> page = this.processoRepository
					.findAllByConsumidoresDenominacaoContainingIgnoreCase(parametro, pageable);
			return transformaDto(pageable, page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listarPorFornecedor(String parametro, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			Page<Processo> page = this.processoRepository
					.findAllByFornecedoresFantasiaContainingIgnoreCaseOrFornecedoresRazaoSocialContainingIgnoreCase(
							parametro, parametro, pageable);
			return transformaDto(pageable, page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listarPorSituacao(Situacao situacao, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			if (situacao.equals(Situacao.EM_ANDAMENTO)) {
				Page<Processo> page = this.processoRepository
						.findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(Situacao.ENCERRADO,
								Situacao.RESOLVIDO, Situacao.NAO_RESOLVIDO, pageable);
				return transformaDto(pageable, page);
			} else {
				Page<Processo> page = this.processoRepository.findAllBySituacao(situacao, pageable);
				return transformaDto(pageable, page);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> listarPorSituacao(Situacao situacao, LocalDate inicio, LocalDate fim) {
		List<Processo> processos = new ArrayList<>();
		if (situacao.equals(Situacao.EM_ANDAMENTO))
			processos = this.processoRepository.findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(
					Situacao.ENCERRADO, Situacao.RESOLVIDO, Situacao.NAO_RESOLVIDO);
		else
			processos = this.processoRepository.findAllBySituacao(situacao);
		List<Processo> filtrados = processos.stream()
				.filter(p -> p.getMovimentacao().get(0).getData().isAfter(inicio)
						&& p.getMovimentacao().get(0).getData().isBefore(fim))
				.collect(Collectors.toList());
		return transformaDto(filtrados);
	}

	public void excluir(Integer id) {
		try {
			this.processoRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<FornecedorNro> ranking(Integer ano) {
		try {
			List<Fornecedor> fornecedores = this.fornecedorService.listar();
			List<FornecedorNro> fornsNro = new ArrayList<>();
			List<Processo> procAno = this.processoRepository
					.findAllByDataBetween(LocalDate.of(ano, 1, 1), LocalDate.of(ano + 1, 1, 1));
			fornecedores.forEach(f -> fornsNro.add(new FornecedorNro(f, 0)));
			for (Processo proc : procAno) {
				for (Fornecedor forn : proc.getFornecedores()) {
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

	public List<Processo> listarPorSituacao(Situacao situacao) {
		return this.processoRepository.findAllBySituacao(situacao);
	}

}
