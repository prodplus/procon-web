package br.com.procon.repositories;

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

}
