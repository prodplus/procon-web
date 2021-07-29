package br.com.procon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Atendimento;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

}
