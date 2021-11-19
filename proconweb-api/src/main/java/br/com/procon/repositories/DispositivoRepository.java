package br.com.procon.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Dispositivo;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {

	List<Dispositivo> findAllByLeiId(Integer idLei);

}
