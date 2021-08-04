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

import br.com.procon.models.Perfil;
import br.com.procon.models.dtos.UsuarioDto;
import br.com.procon.models.forms.UsuarioForm;
import br.com.procon.services.UsuarioService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<UsuarioDto> salvar(@RequestBody UsuarioForm usuario) {
		return ResponseEntity.ok(this.usuarioService.salvar(usuario));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDto> atualizar(@PathVariable Integer id,
			@RequestBody UsuarioForm usuario) {
		return ResponseEntity.ok(this.usuarioService.atualizar(id, usuario));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.usuarioService.buscar(id));
	}

	@GetMapping("/listar/{pagina}/{quant}/{ativos}")
	public ResponseEntity<Page<UsuarioDto>> listar(@PathVariable boolean ativos,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.usuarioService.listar(ativos, pagina, quant));
	}

	@DeleteMapping("/ativar/{id}/{ativar}")
	public ResponseEntity<?> ativar(@PathVariable Integer id, @PathVariable boolean ativar) {
		this.usuarioService.ativar(id, ativar);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.usuarioService.excluir(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/perfis")
	public ResponseEntity<List<Perfil>> getPerfis() {
		return ResponseEntity.ok(this.usuarioService.getPerfis());
	}

	@GetMapping("/login_existe/{email}")
	public ResponseEntity<Boolean> loginExiste(@PathVariable String email) {
		return ResponseEntity.ok(this.usuarioService.loginExiste(email));
	}

}
