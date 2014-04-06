package de.zlvp.model;

public abstract class BaseEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private String bezeichnung;

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	@Override
	public String toString() {
		return super.toString() + " Bezeichnung: " + bezeichnung;
	}

}
