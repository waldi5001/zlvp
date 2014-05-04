package de.zlvp.control.dao;

import org.springframework.data.repository.CrudRepository;

import de.zlvp.model.AbstractEntity;

public interface BaseDao<T extends AbstractEntity> extends CrudRepository<T, Long> {

}
