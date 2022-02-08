package br.com.procon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.procon.models.SetorFiscalizacao;
import br.com.procon.services.SetorFiscalizacaoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/setor_fiscalizacao")
@CrossOrigin("http://localhost:4200")
public class SetorFiscalizacaoController {

	@Autowired
	private SetorFiscalizacaoService setorService;

	@PostMapping
	public ResponseEntity<SetorFiscalizacao> salvar(@RequestBody SetorFiscalizacao setor) {
		return ResponseEntity.ok(this.setorService.salvar(setor));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SetorFiscalizacao> atualizar(@PathVariable Integer id,
			@RequestBody SetorFiscalizacao setor) {
		return ResponseEntity.ok(this.setorService.atualizar(id, setor));
	}

	@GetMapping("/{id}")
	public ResponseEntity<SetorFiscalizacao> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.setorService.buscar(id));
	}

	@GetMapping("/listar")
	public ResponseEntity<List<SetorFiscalizacao>> listar() {
		return ResponseEntity.ok(this.setorService.listar());
	}

	@GetMapping("/listar/{parametro}")
	public ResponseEntity<List<SetorFiscalizacao>> listar(@PathVariable String parametro) {
		return ResponseEntity.ok(this.setorService.listar(parametro));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.setorService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
