package de.zlvp.ui.gruppe;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import de.zlvp.model.Person;

public interface PersonProperties extends PropertyAccess<Person> {

	@Path("id")
	ModelKeyProvider<Person> key();

	@Path("name")
	ValueProvider<Person, String> name();

	@Path("vorname")
	ValueProvider<Person, String> vorname();

	@Path("strasse")
	ValueProvider<Person, String> strasse();

	@Path("plz")
	ValueProvider<Person, String> plz();

	@Path("ort")
	ValueProvider<Person, String> ort();

	@Path("geburtsdatum")
	ValueProvider<Person, Date> geburtsdatum();

}
