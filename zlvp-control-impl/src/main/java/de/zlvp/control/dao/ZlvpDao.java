package de.zlvp.control.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.zlvp.model.Jahr;

public interface ZlvpDao extends CrudRepository<Jahr, Long> {
	@Query("SELECT DISTINCT j FROM Jahr j "
			+ "LEFT JOIN FETCH j.lager l "
			+ "LEFT JOIN FETCH l.gruppe g "
			+ "LEFT JOIN FETCH g.teilnehmer t "
			+ "LEFT JOIN FETCH t.person "
			+ "LEFT JOIN FETCH g.leiter le "
			+ "LEFT JOIN FETCH le.person "
			+ "LEFT JOIN FETCH l.stab s "
			+ "LEFT JOIN FETCH s.person "
			+ "ORDER BY j.bezeichnung DESC")
	List<Jahr> findAllOrderByBezeichnungDesc();
}
