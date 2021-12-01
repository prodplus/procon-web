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

import br.com.procon.models.Area;
import br.com.procon.services.AreaService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/areas")
@CrossOrigin("http://localhost:4200")
public class AreaController {

	@Autowired
	private AreaService areaService;

	@PostMapping
	public ResponseEntity<Area> salvar(@RequestBody Area area) {
		return ResponseEntity.ok(this.areaService.salvar(area));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Area> atualizar(@PathVariable Integer id, @RequestBody Area area) {
		return ResponseEntity.ok(this.areaService.atualizar(id, area));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Area> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.areaService.buscar(id));
	}

	@GetMapping
	public ResponseEntity<List<Area>> listar() {
		return ResponseEntity.ok(this.areaService.listar());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.areaService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
