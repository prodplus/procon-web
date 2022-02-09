package br.com.procon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.models.Fiscalizacao;
import br.com.procon.services.FiscalizacaoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/fiscalizacoes")
@CrossOrigin("http://localhost:4200")
public class FiscalizacaoController {

	@Autowired
	private FiscalizacaoService fiscalizacaoService;

	@PostMapping
	public ResponseEntity<Fiscalizacao> salvar(@RequestBody Fiscalizacao fiscalizacao) {
		return ResponseEntity.ok(this.fiscalizacaoService.salvar(fiscalizacao));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Fiscalizacao> atualizar(@PathVariable Integer id,
			@RequestBody Fiscalizacao fiscalizacao) {
		return ResponseEntity.ok(this.fiscalizacaoService.atualizar(id, fiscalizacao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Fiscalizacao> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.fiscalizacaoService.buscar(id));
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<Fiscalizacao>> listar(@PathVariable int pagina,
			@PathVariable int quant) {
		return ResponseEntity.ok(this.fiscalizacaoService.listar(pagina, quant));
	}

	@GetMapping("/listar/{pagina}/{quant}/{fornecedor}")
	public ResponseEntity<Page<Fiscalizacao>> listar(@PathVariable String fornecedor,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.fiscalizacaoService.listar(fornecedor, pagina, quant));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.fiscalizacaoService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
