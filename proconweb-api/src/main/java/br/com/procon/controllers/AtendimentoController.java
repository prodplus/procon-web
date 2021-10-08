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

import br.com.procon.models.Atendimento;
import br.com.procon.models.dtos.AtendimentoDto;
import br.com.procon.models.forms.AtendimentoForm;
import br.com.procon.services.AtendimentoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/atendimentos")
@CrossOrigin("*")
public class AtendimentoController {

	@Autowired
	private AtendimentoService atendimentoService;

	@PostMapping
	public ResponseEntity<AtendimentoDto> salvar(@RequestBody AtendimentoForm atendimento) {
		return ResponseEntity.ok(this.atendimentoService.salvar(atendimento));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AtendimentoDto> atualizar(@PathVariable Integer id,
			@RequestBody AtendimentoForm atendimento) {
		return ResponseEntity.ok(this.atendimentoService.atualizar(id, atendimento));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Atendimento> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.atendimentoService.buscar(id));
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<AtendimentoDto>> listar(@PathVariable int pagina,
			@PathVariable int quant) {
		return ResponseEntity.ok(this.atendimentoService.listar(pagina, quant));
	}

	@GetMapping("/listar/{pagina}/{quant}/{parametro}")
	public ResponseEntity<Page<AtendimentoDto>> listar(@PathVariable String parametro,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.atendimentoService.listar(parametro, pagina, quant));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.atendimentoService.excluir(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/atendimentosano")
	public ResponseEntity<Long> atendimentosAno() {
		return ResponseEntity.ok(this.atendimentoService.atendimentosAno());
	}

}
