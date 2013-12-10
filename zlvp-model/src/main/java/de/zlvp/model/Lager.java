package de.zlvp.model;

import java.util.Set;

public class Lager extends BaseEntity {

	private Set<Gruppe> gruppe;
	private Set<Stab> stab;

	public Set<Stab> getStab() {
		return stab;
	}

	public void setStab(Set<Stab> stab) {
		this.stab = stab;
	}

	public Set<Gruppe> getGruppe() {
		return gruppe;
	}

	public void setGruppe(Set<Gruppe> gruppe) {
		this.gruppe = gruppe;
	}

}
