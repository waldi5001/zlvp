package de.zlvp.model;

import java.util.HashSet;
import java.util.Set;

public class Jahr extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Set<Lager> lager;

	public Set<Lager> getLager() {
		if (lager == null) {
			lager = new HashSet<Lager>();
		}
		return lager;
	}

	public void setLager(Set<Lager> lager) {
		this.lager = lager;
	}

}
