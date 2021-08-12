package br.com.procon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.models.dtos.ProcDesc;
import br.com.procon.models.dtos.ProcessoDto;
import br.com.procon.services.OperacaoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/operacoes")
public class OperacaoController {

	@Autowired
	private OperacaoService operacaoService;

	@GetMapping("/not_fornecedor")
	public ResponseEntity<List<ProcessoDto>> porNotFornecedor() {
		return ResponseEntity.ok(this.operacaoService.porNotFornecedor());
	}

	@GetMapping("/not_consumidor")
	public ResponseEntity<List<ProcessoDto>> porNotConsumidor() {
		return ResponseEntity.ok(this.operacaoService.porNotConsumidor());
	}

	@GetMapping("/prazo")
	public ResponseEntity<List<ProcessoDto>> porPrazo() {
		return ResponseEntity.ok(this.operacaoService.porPrazo());
	}

	@GetMapping("/prazo_desc")
	public ResponseEntity<List<ProcDesc>> porPrazoDesc() {
		return ResponseEntity.ok(this.operacaoService.porPrazoDesc());
	}

	@GetMapping("/audiencia")
	public ResponseEntity<List<ProcessoDto>> porAudiencia() {
		return ResponseEntity.ok(this.operacaoService.porAudiencia());
	}

	@GetMapping("/audiencia_desc")
	public ResponseEntity<List<ProcDesc>> porAudienciaDesc() {
		return ResponseEntity.ok(this.operacaoService.porAudienciaDesc());
	}

	@GetMapping("/novos")
	public ResponseEntity<List<ProcessoDto>> porNovos() {
		return ResponseEntity.ok(this.operacaoService.getNovos());
	}

}
