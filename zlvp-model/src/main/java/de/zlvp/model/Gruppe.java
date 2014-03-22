package de.zlvp.model;

import java.util.HashSet;
import java.util.Set;

public class Gruppe extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Set<Leiter> leiter;
	private Set<Teilnehmer> teilnehmer;

	public Set<Leiter> getLeiter() {
		if (leiter == null) {
			leiter = new HashSet<Leiter>();
		}
		return leiter;
	}

	public void setLeiter(Set<Leiter> leiter) {
		this.leiter = leiter;
	}

	public Set<Teilnehmer> getTeilnehmer() {
		if (teilnehmer == null) {
			teilnehmer = new HashSet<Teilnehmer>();
		}
		return teilnehmer;
	}

	public void setTeilnehmer(Set<Teilnehmer> teilnehmer) {
		this.teilnehmer = teilnehmer;
	}

}
