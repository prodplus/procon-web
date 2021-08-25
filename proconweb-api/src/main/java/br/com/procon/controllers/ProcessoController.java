package br.com.procon.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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

import br.com.procon.models.Processo;
import br.com.procon.models.auxiliares.FornecedorNro;
import br.com.procon.models.dtos.ProcessoDto;
import br.com.procon.models.enums.Situacao;
import br.com.procon.models.forms.ProcessoForm;
import br.com.procon.services.ProcessoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/processos")
@CrossOrigin("*")
public class ProcessoController {

	@Autowired
	private ProcessoService processoService;

	@PostMapping
	public ResponseEntity<ProcessoDto> salvar(@RequestBody ProcessoForm processo) {
		return ResponseEntity.ok(this.processoService.salvar(processo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProcessoDto> atualizar(@PathVariable Integer id,
			@RequestBody ProcessoForm processo) {
		return ResponseEntity.ok(this.processoService.atualizar(id, processo));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Processo> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.processoService.buscar(id));
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<ProcessoDto>> listar(@PathVariable int pagina,
			@PathVariable int quant) {
		return ResponseEntity.ok(this.processoService.listar(pagina, quant));
	}

	@GetMapping("/listar_autos/{pagina}/{quant}/{autos}")
	public ResponseEntity<Page<ProcessoDto>> listarPorAutos(@PathVariable String autos,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.processoService.listarPorAutos(autos, pagina, quant));
	}

	@GetMapping("/listar_consumidor/{pagina}/{quant}/{parametro}")
	public ResponseEntity<Page<ProcessoDto>> listarPorConsumidor(@PathVariable String parametro,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity
				.ok(this.processoService.listarPorConsumidor(parametro, pagina, quant));
	}

	@GetMapping("/listar_fornecedor/{pagina}/{quant}/{parametro}")
	public ResponseEntity<Page<ProcessoDto>> listarPorFornecedor(@PathVariable String parametro,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity
				.ok(this.processoService.listarPorFornecedor(parametro, pagina, quant));
	}

	@GetMapping("/listar_situacao/{pagina}/{quant}/{situacao}")
	public ResponseEntity<Page<ProcessoDto>> listarPorSituacao(@PathVariable Situacao situacao,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.processoService.listarPorSituacao(situacao, pagina, pagina));
	}

	@GetMapping("/listar_sit_data/{situacao}/{inicio}/{fim}")
	public ResponseEntity<List<ProcessoDto>> listarPorSituacao(@PathVariable Situacao situacao,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
		return ResponseEntity.ok(this.processoService.listarPorSituacao(situacao, inicio, fim));
	}

	@GetMapping("/listar_sit_puro/{situacao}")
	public ResponseEntity<List<ProcessoDto>> listarPorSituacaoPuro(
			@PathVariable Situacao situacao) {
		return ResponseEntity.ok(this.processoService.listarPorSituacaoPuro(situacao));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.processoService.excluir(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/ranking/{ano}")
	public ResponseEntity<List<FornecedorNro>> ranking(@PathVariable Integer ano) {
		return ResponseEntity.ok(this.processoService.ranking(ano));
	}

}
