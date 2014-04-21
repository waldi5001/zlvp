package de.zlvp.control.dao;

import org.springframework.data.repository.CrudRepository;

import de.zlvp.model.Person;

public interface PersonDao extends CrudRepository<Person, Long> {

	Person findByIdAndVersion(Long ids, Long versions);

}
