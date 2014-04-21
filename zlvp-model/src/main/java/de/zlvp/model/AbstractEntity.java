package de.zlvp.model;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private long version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return getClass() + ": Id: " + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractEntity) {
			AbstractEntity ae = (AbstractEntity) obj;
			return id == ae.id && version == ae.version;
		} else {
			return false;
		}
	}

}
