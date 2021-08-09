package br.com.procon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Atendimento;
import br.com.procon.models.Processo;
import br.com.procon.report.AtendIni;
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

}
