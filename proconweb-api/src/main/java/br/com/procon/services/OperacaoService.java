package br.com.procon.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Processo;
import br.com.procon.models.dtos.ProcDesc;
import br.com.procon.models.dtos.ProcessoDto;
import br.com.procon.models.enums.Situacao;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class OperacaoService {

	@Autowired
	private ProcessoService processoService;

	public List<ProcessoDto> porNotFornecedor() {
		try {
			List<Processo> processos = this.processoService
					.listarPorSituacao(Situacao.NOTIFICAR_FORNECEDOR);
			Collections.sort(processos, new NotCompare());
			return toDto(processos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> porNotConsumidor() {
		try {
			List<Processo> processos = this.processoService
					.listarPorSituacao(Situacao.NOTIFICAR_CONSUMIDOR);
			Collections.sort(processos, new NotCompare());
			return toDto(processos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> porPrazo() {
		try {
			List<Processo> processos = this.processoService.listarPorSituacao(Situacao.PRAZO);
			processos.addAll(this.processoService.listarPorSituacao(Situacao.PRAZO_FORNECEDOR));
			processos.addAll(this.processoService.listarPorSituacao(Situacao.PRAZO_CONSUMIDOR));
			processos.addAll(this.processoService.listarPorSituacao(Situacao.AGUARDA_AR_FORN));
			processos.addAll(this.processoService.listarPorSituacao(Situacao.AGUARDA_AR_CONS));
			Collections.sort(processos, new PrazoCompare());
			return toDto(processos.stream()
					.filter(p -> p.getMovimentacao().get(0).getAuxD().isBefore(LocalDate.now()))
					.collect(Collectors.toList()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcDesc> porPrazoDesc() {
		try {
			List<Processo> processos = this.processoService.listarPorSituacao(Situacao.PRAZO);
			processos.addAll(this.processoService.listarPorSituacao(Situacao.PRAZO_FORNECEDOR));
			processos.addAll(this.processoService.listarPorSituacao(Situacao.PRAZO_CONSUMIDOR));
			processos.addAll(this.processoService.listarPorSituacao(Situacao.AGUARDA_AR_FORN));
			processos.addAll(this.processoService.listarPorSituacao(Situacao.AGUARDA_AR_CONS));
			Collections.sort(processos, new PrazoCompare());
			return toDtoDesc(processos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> porAudiencia() {
		try {
			List<Processo> processos = this.processoService.listarPorSituacao(Situacao.AUDIENCIA);
			Collections.sort(processos, new AudienciaCompare());
			return toDto(processos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcDesc> porAudienciaDesc() {
		try {
			List<Processo> processos = this.processoService.listarPorSituacao(Situacao.AUDIENCIA);
			Collections.sort(processos, new AudienciaCompare());
			List<ProcDesc> retorno = new ArrayList<>();
			processos.forEach(p -> {
				retorno.add(new ProcDesc(p,
						String.format("%02d/%02d %02d:%02d",
								p.getMovimentacao().get(0).getAuxD().getDayOfMonth(),
								p.getMovimentacao().get(0).getAuxD().getMonthValue(),
								p.getMovimentacao().get(0).getAuxT().getHour(),
								p.getMovimentacao().get(0).getAuxT().getMinute())));
			});
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> getNovos() {
		try {
			List<Processo> processos = this.processoService.listarPorSituacao(Situacao.AUDIENCIA);
			Collections.sort(processos, new NotCompare());
			return toDto(processos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private static List<ProcessoDto> toDto(List<Processo> processos) {
		List<ProcessoDto> dtos = new ArrayList<>();
		processos.forEach(p -> dtos.add(new ProcessoDto(p)));
		return dtos;
	}

	private static List<ProcDesc> toDtoDesc(List<Processo> processos) {
		List<ProcDesc> dtos = new ArrayList<>();
		processos.forEach(p -> {
			String vencimento;
			if (p.getMovimentacao() != null && p.getMovimentacao().get(0).getAuxD() != null) {
				if (p.getMovimentacao().get(0).getAuxD().isBefore(LocalDate.now()))
					vencimento = String.format("Vencido em %02d/%02d/%02d",
							p.getMovimentacao().get(0).getAuxD().getDayOfMonth(),
							p.getMovimentacao().get(0).getAuxD().getMonthValue(),
							p.getMovimentacao().get(0).getAuxD().getYear());
				else
					vencimento = String.format("A vencer em %02d/%02d/%02d",
							p.getMovimentacao().get(0).getAuxD().getDayOfMonth(),
							p.getMovimentacao().get(0).getAuxD().getMonthValue(),
							p.getMovimentacao().get(0).getAuxD().getYear());				
			} else {
				vencimento = "Sem data de vencimento!";
			}
			dtos.add(new ProcDesc(p, vencimento));
		});
		return dtos;
	}

	class NotCompare implements Comparator<Processo> {

		@Override
		public int compare(Processo p1, Processo p2) {
			return p1.getMovimentacao().get(0).getData()
					.compareTo(p2.getMovimentacao().get(0).getData());
		}

	}

	class PrazoCompare implements Comparator<Processo> {

		@Override
		public int compare(Processo p1, Processo p2) {
			LocalDate p1D = p1.getMovimentacao().get(0).getAuxD();
			LocalDate p2D = p2.getMovimentacao().get(0).getAuxD();
			if (p1D != null && p2D != null)
				return p1D.compareTo(p2D);
			else if (p1.getData() != null && p2.getData() != null)
				return p1.getData().compareTo(p2.getData());
			return 0;
		}

	}

	class AudienciaCompare implements Comparator<Processo> {

		@Override
		public int compare(Processo p1, Processo p2) {
			if (p1.getMovimentacao().get(0).getAuxD() != null
					&& p2.getMovimentacao().get(0).getAuxD() != null)
				if (p1.getMovimentacao().get(0).getAuxD()
						.compareTo(p2.getMovimentacao().get(0).getAuxD()) == 0)
					if (p1.getMovimentacao().get(0).getAuxT() != null
							&& p2.getMovimentacao().get(0).getAuxT() != null)
						return p1.getMovimentacao().get(0).getAuxT()
								.compareTo(p2.getMovimentacao().get(0).getAuxT());
					else
						return 0;
				else
					return p1.getMovimentacao().get(0).getAuxD()
							.compareTo(p2.getMovimentacao().get(0).getAuxD());
			else
				return 0;
		}

	}

}
