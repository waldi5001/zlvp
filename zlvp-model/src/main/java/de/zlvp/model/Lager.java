package de.zlvp.model;

import java.util.HashSet;
import java.util.Set;

public class Lager extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Set<Gruppe> gruppe;
	private Set<Stab> stab;

	public Set<Stab> getStab() {
		if (stab == null) {
			stab = new HashSet<Stab>();
		}
		return stab;
	}

	public void setStab(Set<Stab> stab) {
		this.stab = stab;
	}

	public Set<Gruppe> getGruppe() {
		if (gruppe == null) {
			gruppe = new HashSet<Gruppe>();
		}
		return gruppe;
	}

	public void setGruppe(Set<Gruppe> gruppe) {
		this.gruppe = gruppe;
	}

}
