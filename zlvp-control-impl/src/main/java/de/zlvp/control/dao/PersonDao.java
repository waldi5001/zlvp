package de.zlvp.control.dao;

import org.springframework.data.repository.CrudRepository;

import de.zlvp.model.Person;

public interface PersonDao extends BaseDao<Person>, CrudRepository<Person, Long> {

}
