package de.zlvp.control.impl;

import java.util.Arrays;

import javax.persistence.OptimisticLockException;

import de.zlvp.control.dao.BaseDao;
import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.AbstractEntity;

public abstract class AbstractController {

	public <T extends AbstractEntity> T findOrCreate(Class<T> clazz, BaseDao<T> dao, EntityInfo<T> info) {
		if (info == null) {
			try {
				T newInstance = clazz.newInstance();
				dao.save(newInstance);
				return newInstance;
			} catch (InstantiationException | IllegalAccessException e1) {
				throw new RuntimeException(e1);
			}
		} else {
			final T e = dao.findAll(Arrays.asList(info.getId())).iterator().next();
			assertVersion(e, info);
			return e;
		}

	}

	private <T extends AbstractEntity> void assertVersion(T e, EntityInfo<T> info) {
		if (e.getVersion() != info.getVersion()) {
			throw new OptimisticLockException(e);
		}
	}
}
