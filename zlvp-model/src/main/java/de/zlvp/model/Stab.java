package de.zlvp.model;

public class Stab extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private Person person;
	private Funktion funktion;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Funktion getFunktion() {
		return funktion;
	}

	public void setFunktion(Funktion funktion) {
		this.funktion = funktion;
	}

}
