package de.zlvp.control.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.zlvp.model.AbstractEntity;
import de.zlvp.model.Leiter;
import de.zlvp.model.Person;
import de.zlvp.model.Teilnehmer;

public class EntityInfo<T extends AbstractEntity> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long version;

	public EntityInfo() {
	}

	private EntityInfo(Long id, Long version) {
		this.id = id;
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public static <T extends AbstractEntity> EntityInfo<T> of(T entity) {
		return new EntityInfo<T>(entity.getId(), entity.getVersion());
	}

	public static <T extends AbstractEntity> Collection<EntityInfo<T>> of(Collection<T> col) {
		Set<EntityInfo<T>> set = new HashSet<EntityInfo<T>>();
		for (T t : col) {
			set.add(EntityInfo.of(t));
		}
		return set;
	}

	public static Collection<EntityInfo<Person>> ofLeiter(Collection<Leiter> entity) {
		Set<EntityInfo<Person>> pl = new HashSet<EntityInfo<Person>>();
		for (Leiter l : entity) {
			pl.add(EntityInfo.of(l.getPerson()));
		}
		return pl;
	}

	public static Collection<EntityInfo<Person>> ofTeilnehmer(Collection<Teilnehmer> entity) {
		Set<EntityInfo<Person>> pl = new HashSet<EntityInfo<Person>>();
		for (Teilnehmer l : entity) {
			pl.add(EntityInfo.of(l.getPerson()));
		}
		return pl;
	}
}
