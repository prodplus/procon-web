package br.com.procon.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Atendimento;
import br.com.procon.models.Fiscalizacao;
import br.com.procon.models.Fornecedor;
import br.com.procon.models.Processo;
import br.com.procon.models.SetorFiscalizacao;
import br.com.procon.models.auxiliares.Movimento;
import br.com.procon.models.enums.Situacao;
import br.com.procon.report.AtendIni;
import br.com.procon.report.ConvAudCons;
import br.com.procon.report.ConvAudForn;
import br.com.procon.report.DespachoAud;
import br.com.procon.report.DespachoNot;
import br.com.procon.report.NotCincoDias;
import br.com.procon.report.NotConsumidor;
import br.com.procon.report.NotDezDias;
import br.com.procon.report.NotImpugnacao;
import br.com.procon.report.NotMulta;
import br.com.procon.report.Oficio;
import br.com.procon.report.ProcIni;
import br.com.procon.report.TermoVisita;
import br.com.procon.report.TermosBranco;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class DocumentoService {

	@Autowired
	private AtendimentoService atendimentoService;
	@Autowired
	private ProcessoService processoService;
	@Autowired
	private FornecedorService fornecedorService;
	@Autowired
	private FiscalizacaoService fiscalizacaoService;
	@Autowired
	private SetorFiscalizacaoService setorService;

	public InputStreamResource atendimento(Integer id) {
		try {
			Atendimento atendimento = this.atendimentoService.buscar(id);
			return new InputStreamResource(AtendIni.gerar(atendimento));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource inicial(Integer id) {
		try {
			Processo processo = this.processoService.buscar(id);
			return new InputStreamResource(ProcIni.gerar(processo));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource notDezDias(Integer idProcesso, Integer idFornecedor) {
		try {
			Processo processo = this.processoService.buscar(idProcesso);
			Fornecedor fornecedor = this.fornecedorService.buscar(idFornecedor);
			return new InputStreamResource(NotDezDias.gerar(processo, fornecedor));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource notCincoDias(Integer idProcesso, Integer idFornecedor) {
		try {
			Processo processo = this.processoService.buscar(idProcesso);
			Fornecedor fornecedor = this.fornecedorService.buscar(idFornecedor);
			return new InputStreamResource(NotCincoDias.gerar(processo, fornecedor));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource notImpugnacao(Integer idProcesso, Integer idFornecedor) {
		try {
			Processo processo = this.processoService.buscar(idProcesso);
			Fornecedor fornecedor = this.fornecedorService.buscar(idFornecedor);
			return new InputStreamResource(NotImpugnacao.gerar(processo, fornecedor));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource notMulta(Integer idProcesso, Integer idFornecedor) {
		try {
			Processo processo = this.processoService.buscar(idProcesso);
			Fornecedor fornecedor = this.fornecedorService.buscar(idFornecedor);
			return new InputStreamResource(NotMulta.gerar(processo, fornecedor));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource oficio(Integer id) {
		try {
			Processo processo = this.processoService.buscar(id);
			return new InputStreamResource(Oficio.gerar(processo));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource notConsumidor(Integer id) {
		try {
			Processo processo = this.processoService.buscar(id);
			return new InputStreamResource(NotConsumidor.gerar(processo));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource convAudCons(Integer id, Movimento movimento) {
		try {
			Processo processo = this.processoService.buscar(id);
			return new InputStreamResource(ConvAudCons.gerar(processo, movimento));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource convAudForn(Integer id, Movimento movimento) {
		try {
			Processo processo = this.processoService.buscar(id);
			return new InputStreamResource(ConvAudForn.gerar(processo, movimento));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource despachoAud(Integer id, Movimento movimento) {
		try {
			Processo processo = this.processoService.buscar(id);
			processo.getMovimentacao().add(0, movimento);
			return new InputStreamResource(
					DespachoAud.gerar(this.processoService.atualizar(id, processo), movimento));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource despachoNot(Integer id) {
		try {
			Processo processo = this.processoService.buscar(id);
			processo.getMovimentacao().add(0, new Movimento(LocalDate.now(), Situacao.DESPACHO,
					Situacao.NOTIFICAR_FORNECEDOR, "", null, null));
			return new InputStreamResource(
					DespachoNot.gerar(this.processoService.atualizar(id, processo)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public InputStreamResource termoVisita(Integer id) {
		try {
			Fiscalizacao fiscalizacao = this.fiscalizacaoService.buscar(id);
			return new InputStreamResource(TermoVisita.gerar(fiscalizacao));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}
	
	public InputStreamResource termoVisita(Integer idFornecedor, Integer idSetor) {
		try {
			Fornecedor fornecedor = this.fornecedorService.buscar(idFornecedor);
			SetorFiscalizacao setor = this.setorService.buscar(idSetor);
			return new InputStreamResource(TermosBranco.gerar(fornecedor, setor));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
