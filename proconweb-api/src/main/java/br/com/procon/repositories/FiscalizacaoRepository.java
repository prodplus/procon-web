package br.com.procon.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Fiscalizacao;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface FiscalizacaoRepository extends JpaRepository<Fiscalizacao, Integer> {

	Page<Fiscalizacao> findAllByFornecedorRazaoSocialContainingIgnoreCase(String fornecedor,
			Pageable pageable);

}
