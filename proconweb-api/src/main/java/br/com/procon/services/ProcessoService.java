package br.com.procon.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import br.com.procon.models.auxiliares.ProcessoMovimentacao;
import br.com.procon.models.dtos.ProcessoDto;
import br.com.procon.models.enums.Situacao;
import br.com.procon.models.enums.TipoLog;
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
	@Autowired
	private LogService logService;

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
			Processo proc = this.processoRepository.save(processo.converter(this.consumidorService,
					this.fornecedorService, this.usuarioService));
			this.logService.salvar(LocalDateTime.now(), "Processo " + proc.getAutos(),
					TipoLog.INSERCAO);
			return new ProcessoDto(proc);
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
				this.logService.salvar(LocalDateTime.now(), "Processo " + novo.getAutos(),
						TipoLog.ATUALIZACAO);
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

	public Processo atualizar(Integer id, Processo processo) {
		return this.processoRepository.findById(id).map(novo -> {
			novo.setAtendente(processo.getAtendente());
			novo.setAutos(processo.getAutos());
			novo.setConsumidores(processo.getConsumidores());
			novo.setData(processo.getData());
			novo.setFornecedores(processo.getFornecedores());
			novo.setMovimentacao(processo.getMovimentacao());
			novo.setRelato(processo.getRelato());
			novo.setRepresentantes(processo.getRepresentantes());
			novo.setSituacao(processo.getSituacao());
			novo.setTipo(processo.getTipo());
			if (novo.getMovimentacao() == null || novo.getMovimentacao().isEmpty())
				novo.getMovimentacao().add(new Movimento(novo.getData(), Situacao.BALCAO,
						Situacao.AUTUADO, "recém autuado", null, null));
			novo.setSituacao(ProcessoUtils.handleSituacao(novo.getMovimentacao()));
			this.logService.salvar(LocalDateTime.now(), "Processo " + novo.getAutos(),
					TipoLog.ATUALIZACAO);
			return this.processoRepository.save(novo);
		}).orElseThrow(() -> new EntityNotFoundException());
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

	public List<ProcessoDto> listarPorSituacaoPuro(Situacao situacao) {
		try {
			if (situacao.equals(Situacao.EM_ANDAMENTO))
				return transformaDto(
						this.processoRepository.findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(
								Situacao.ENCERRADO, Situacao.RESOLVIDO, Situacao.NAO_RESOLVIDO));
			return transformaDto(this.processoRepository.findAllBySituacao(situacao));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> listarPorSituacao(Situacao situacao, LocalDate inicio, LocalDate fim) {
		List<Processo> processos = new ArrayList<>();
		if (situacao.equals(Situacao.AUTUADO))
			processos = this.processoRepository.findAllByDataBetween(inicio, fim);
		else {
			processos = this.processoRepository.findAllBySituacao(situacao);
			List<Processo> filtrados = processos.stream()
					.filter(p -> p.getMovimentacao().get(0).getData().isAfter(inicio)
							&& p.getMovimentacao().get(0).getData().isBefore(fim))
					.collect(Collectors.toList());
			return transformaDto(filtrados);
		}
		return transformaDto(processos);
	}

	public void excluir(Integer id) {
		try {
			Processo proc = this.processoRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			this.logService.salvar(LocalDateTime.now(), "Processo " + proc.getAutos(),
					TipoLog.EXCLUSAO);
			this.processoRepository.delete(proc);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "processo não localizado!",
					e.getCause());
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
		try {
			if (situacao.equals(Situacao.EM_ANDAMENTO)) {
				return this.processoRepository.findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(
						Situacao.ENCERRADO, Situacao.RESOLVIDO, Situacao.NAO_RESOLVIDO);
			} else
				return this.processoRepository.findAllBySituacao(situacao);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Processo> listarPorSituacaoEAutos(Situacao situacao, String autos) {
		try {
			if (situacao.equals(Situacao.EM_ANDAMENTO)) {
				return this.processoRepository
						.findAllBySituacaoNotAndSituacaoNotAndSituacaoNotAndAutosContaining(
								Situacao.ENCERRADO, Situacao.RESOLVIDO, Situacao.NAO_RESOLVIDO,
								autos);
			} else
				return this.processoRepository.findAllBySituacaoAndAutosContaining(situacao, autos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoMovimentacao> movimentacaoDia(LocalDate data) {
		Set<Processo> movimentados = new HashSet<>(
				this.processoRepository.findAllByMovimentacaoData(data));
		List<ProcessoMovimentacao> procMov = new ArrayList<>();
		movimentados.forEach(p -> {
			List<Movimento> internos = new ArrayList<>();
			p.getMovimentacao().forEach(m -> {
				if (m.getData().equals(data))
					internos.add(m);
			});
			Collections.reverse(internos);
			internos.forEach(i -> procMov.add(new ProcessoMovimentacao(new ProcessoDto(p), i)));
		});
		Collections.sort(procMov);
		return procMov;
	}

}
