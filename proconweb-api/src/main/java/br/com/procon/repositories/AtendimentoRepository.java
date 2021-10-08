package br.com.procon.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	Page<Atendimento> findAllByConsumidoresDenominacaoContainingIgnoreCaseOrFornecedoresFantasiaContainingIgnoreCaseOrFornecedoresRazaoSocialContainingIgnoreCase(
			String parametro, String parametro2, String parametro3, Pageable pageable);

	long countByDataAfter(LocalDate of);

}
