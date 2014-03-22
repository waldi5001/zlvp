package de.zlvp.model;

public class Leiter extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private Person person;
	private Gruppe gruppe;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Gruppe getGruppe() {
		return gruppe;
	}

	public void setGruppe(Gruppe gruppe) {
		this.gruppe = gruppe;
	}

}
