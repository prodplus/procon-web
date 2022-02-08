package br.com.procon.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.SetorFiscalizacao;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface SetorFiscalizacaoRepository extends JpaRepository<SetorFiscalizacao, Integer> {

	List<SetorFiscalizacao> findAllByDescricaoContaining(String parametro, Sort by);

}
