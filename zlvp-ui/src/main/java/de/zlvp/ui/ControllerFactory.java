package de.zlvp.ui;

import com.google.gwt.core.client.GWT;

import de.zlvp.control.GruppeController;
import de.zlvp.control.GruppeControllerAsync;
import de.zlvp.control.PersonController;
import de.zlvp.control.PersonControllerAsync;
import de.zlvp.control.ZlvpController;
import de.zlvp.control.ZlvpControllerAsync;

public class ControllerFactory {

	private static final ZlvpControllerAsync zlvpController = GWT.create(ZlvpController.class);
	private static final GruppeControllerAsync gruppeController = GWT.create(GruppeController.class);
	private static final PersonControllerAsync personController = GWT.create(PersonController.class);

	public static ZlvpControllerAsync getZlvpController() {
		return zlvpController;
	}

	public static GruppeControllerAsync getGruppeController() {
		return gruppeController;
	}

	public static PersonControllerAsync getPersonController() {
		return personController;
	}

}
