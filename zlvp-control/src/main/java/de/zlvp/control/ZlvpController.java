package de.zlvp.control;

import java.util.List;

import de.zlvp.model.Jahr;

public interface ZlvpController {

	List<Jahr> getJahre();

	Jahr getJahr(long id);

}
