package br.com.procon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Assunto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface AssuntoRepository extends JpaRepository<Assunto, Integer> {

}
