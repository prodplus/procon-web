package br.com.procon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Atendimento;
import br.com.procon.models.Fornecedor;
import br.com.procon.models.Processo;
import br.com.procon.models.auxiliares.Movimento;
import br.com.procon.report.AtendIni;
import br.com.procon.report.ConvAudCons;
import br.com.procon.report.ConvAudForn;
import br.com.procon.report.NotCincoDias;
import br.com.procon.report.NotConsumidor;
import br.com.procon.report.NotDezDias;
import br.com.procon.report.NotImpugnacao;
import br.com.procon.report.NotMulta;
import br.com.procon.report.Oficio;
import br.com.procon.report.ProcIni;

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

}
