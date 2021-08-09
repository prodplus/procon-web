package br.com.procon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.services.DocumentoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/documentos")
@CrossOrigin("*")
public class DocumentoController {

	@Autowired
	private DocumentoService documentoService;

	@GetMapping(path = "/atendimento/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> atendimento(@PathVariable Integer id) {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=atendimento.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(this.documentoService.atendimento(id));
	}

	@GetMapping(path = "/inicial/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> inicial(@PathVariable Integer id) {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=inicial.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(this.documentoService.inicial(id));
	}

}
