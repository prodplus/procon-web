package br.com.procon.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.models.enums.Situacao;
import br.com.procon.models.enums.TipoPessoa;
import br.com.procon.models.enums.TipoProcesso;
import br.com.procon.models.enums.UF;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/enums")
public class EnumController {

	@GetMapping("/situacoes")
	public ResponseEntity<Situacao[]> getSituacoes() {
		return ResponseEntity.ok(Situacao.values());
	}

	@GetMapping("/tipos_pessoa")
	public ResponseEntity<TipoPessoa[]> getTiposPessoa() {
		return ResponseEntity.ok(TipoPessoa.values());
	}

	@GetMapping("/tipos_processo")
	public ResponseEntity<TipoProcesso[]> getTiposProcesso() {
		return ResponseEntity.ok(TipoProcesso.values());
	}
	
	@GetMapping("/ufs")
	public ResponseEntity<UF[]> getUfs() {
		return ResponseEntity.ok(UF.values());
	}

}
