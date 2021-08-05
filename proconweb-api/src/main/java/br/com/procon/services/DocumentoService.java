package br.com.procon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Atendimento;
import br.com.procon.report.AtendIni;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class DocumentoService {

	@Autowired
	private AtendimentoService atendimentoService;

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

}
