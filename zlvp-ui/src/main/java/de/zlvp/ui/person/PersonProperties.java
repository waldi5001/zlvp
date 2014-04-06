package de.zlvp.ui.person;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import de.zlvp.ui.person.AbstractPersonGrid.PersonUi;
import de.zlvp.ui.person.AbstractPersonGrid.Role;

public interface PersonProperties extends PropertyAccess<PersonUi> {

	@Path("id")
	ModelKeyProvider<PersonUi> key();

	ValueProvider<PersonUi, String> name();

	ValueProvider<PersonUi, String> vorname();

	ValueProvider<PersonUi, String> strasse();

	ValueProvider<PersonUi, String> plz();

	ValueProvider<PersonUi, String> ort();

	ValueProvider<PersonUi, Date> geburtsdatum();

	ValueProvider<PersonUi, Role> role();
}
