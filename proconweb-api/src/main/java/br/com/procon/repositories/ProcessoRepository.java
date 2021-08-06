package br.com.procon.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Processo;
import br.com.procon.models.enums.Situacao;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Integer> {

	List<Processo> findAllByDataBetween(LocalDate inicio, LocalDate fim);

	Page<Processo> findAllByAutosContaining(String autos, Pageable pageable);

	Page<Processo> findAllByConsumidoresDenominacaoContainingIgnoreCase(String parametro,
			Pageable pageable);

	Page<Processo> findAllByFornecedoresFantasiaContainingIgnoreCaseOrFornecedoresRazaoSocialContainingIgnoreCase(
			String parametro, String parametro2, Pageable pageable);

	Page<Processo> findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(Situacao encerrado,
			Situacao resolvido, Situacao naoResolvido, Pageable pageable);

	Page<Processo> findAllBySituacao(Situacao situacao, Pageable pageable);

}
