package br.com.procon.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Lei;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface LeiRepository extends JpaRepository<Lei, Integer> {

	List<Lei> findAllByDescricaoContainingIgnoreCase(String descricao);

	Page<Lei> findAllByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

}
