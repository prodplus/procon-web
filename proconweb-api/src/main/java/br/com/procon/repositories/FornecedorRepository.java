package br.com.procon.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Fornecedor;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

	Page<Fornecedor> findAllByFantasiaContainingIgnoreCaseOrRazaoSocialContainingIgnoreCaseOrCnpjContaining(
			String parametro, String parametro2, String parametro3, Pageable pageable);

	boolean existsByFantasia(String fantasia);

	boolean existsByCnpj(String cnpj);

}
