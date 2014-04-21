package de.zlvp.control.dao;

import org.springframework.data.repository.CrudRepository;

import de.zlvp.model.Gruppe;

public interface GruppeDao extends CrudRepository<Gruppe, Long> {

	Gruppe findByIdAndVersion(Long id, Long version);

}
