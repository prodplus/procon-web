package br.com.procon.controllers;

import java.util.List;

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

import br.com.procon.models.Dispositivo;
import br.com.procon.models.Lei;
import br.com.procon.services.LeiService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/leis")
public class LeiController {

	@Autowired
	private LeiService leiService;

	@PostMapping
	public ResponseEntity<Lei> salvar(@RequestBody Lei lei) {
		return ResponseEntity.ok(this.leiService.salvar(lei));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Lei> atualizar(@PathVariable Integer id, @RequestBody Lei lei) {
		return ResponseEntity.ok(this.leiService.atualizar(id, lei));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Lei> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.leiService.buscar(id));
	}

	@GetMapping("/listar")
	public ResponseEntity<List<Lei>> listar() {
		return ResponseEntity.ok(this.leiService.listar());
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<Lei>> listar(@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.leiService.listar(pagina, quant));
	}

	@GetMapping("/listar/{descricao}")
	public ResponseEntity<List<Lei>> listar(@PathVariable String descricao) {
		return ResponseEntity.ok(this.leiService.listar(descricao));
	}

	@GetMapping("/listar/{pagina}/{quant}/{descricao}")
	public ResponseEntity<Page<Lei>> listar(@PathVariable int pagina, @PathVariable int quant,
			@PathVariable String descricao) {
		return ResponseEntity.ok(this.leiService.listar(pagina, quant, descricao));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.leiService.excluir(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/dispositivo")
	public ResponseEntity<Dispositivo> salvar(@RequestBody Dispositivo dispositivo) {
		return ResponseEntity.ok(this.leiService.salvar(dispositivo));
	}

	@DeleteMapping("/dispositivo/{id}")
	public ResponseEntity<?> excluirDispo(@PathVariable Integer id) {
		this.leiService.excluirDispo(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/dispositivos/{idLei}")
	public ResponseEntity<List<Dispositivo>> listarDispositivos(@PathVariable Integer idLei) {
		return ResponseEntity.ok(this.leiService.listarDispositivos(idLei));
	}

}
