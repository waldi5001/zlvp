package de.zlvp.model;

public abstract class BaseEntity extends AbstractEntity {
	
	private String bezeichnung;

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

}
