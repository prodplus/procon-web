package br.com.procon.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Usuario;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Page<Usuario> findAllByAtivo(boolean ativos, Pageable pageable);

	boolean existsByEmail(String email);

	Optional<Usuario> findByEmail(String username);

}
