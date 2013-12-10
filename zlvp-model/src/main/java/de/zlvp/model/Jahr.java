package de.zlvp.model;

import java.util.Set;

public class Jahr extends BaseEntity {

	private Set<Lager> lager;

	public Set<Lager> getLager() {
		return lager;
	}

	public void setLager(Set<Lager> lager) {
		this.lager = lager;
	}

}
