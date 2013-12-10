package de.zlvp.model;

import java.util.Set;

public class Gruppe extends BaseEntity {

	private Set<Leiter> leiter;
	private Set<Teilnehmer> teilnehmer;

	public Set<Leiter> getLeiter() {
		return leiter;
	}

	public void setLeiter(Set<Leiter> leiter) {
		this.leiter = leiter;
	}

	public Set<Teilnehmer> getTeilnehmer() {
		return teilnehmer;
	}

	public void setTeilnehmer(Set<Teilnehmer> teilnehmer) {
		this.teilnehmer = teilnehmer;
	}

}
