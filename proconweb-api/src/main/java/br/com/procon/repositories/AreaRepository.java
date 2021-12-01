package br.com.procon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Area;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

}
