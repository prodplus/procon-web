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

	@GetMapping(path = "/not_dez_dias/{idProcesso}/{idFornecedor}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> notDezDias(@PathVariable Integer idProcesso,
			@PathVariable Integer idFornecedor) {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=not_dez.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(this.documentoService.notDezDias(idProcesso, idFornecedor));
	}

	@GetMapping(path = "/not_cinco_dias/{idProcesso}/{idFornecedor}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> notCincoDias(@PathVariable Integer idProcesso,
			@PathVariable Integer idFornecedor) {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=not_cinco.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(this.documentoService.notCincoDias(idProcesso, idFornecedor));
	}

	@GetMapping(path = "/not_impugnacao/{idProcesso}/{idFornecedor}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> notImpugnacao(@PathVariable Integer idProcesso,
			@PathVariable Integer idFornecedor) {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=not_impugna.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(this.documentoService.notImpugnacao(idProcesso, idFornecedor));
	}

	@GetMapping(path = "/not_multa/{idProcesso}/{idFornecedor}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> notMulta(@PathVariable Integer idProcesso,
			@PathVariable Integer idFornecedor) {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=not_multa.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(this.documentoService.notMulta(idProcesso, idFornecedor));
	}

	@GetMapping(path = "/oficio/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> oficio(@PathVariable Integer id) {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=oficio.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(this.documentoService.oficio(id));
	}

	@GetMapping(path = "/not_consumidor/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> notConsumidor(@PathVariable Integer id) {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=not_.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(this.documentoService.notConsumidor(id));
	}

}
