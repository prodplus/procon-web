package br.com.procon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Processo;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Integer> {

}
