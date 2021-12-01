package br.com.procon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.procon.models.Assunto;
import br.com.procon.repositories.AssuntoRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class AssuntoService {
	
	@Autowired
	private AssuntoRepository assuntoRepository;
	
	public Assunto salvar(Assunto assunto) {
		return this.assuntoRepository.save(assunto);
	}

}
