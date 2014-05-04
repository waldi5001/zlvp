package de.zlvp.control;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.Person;

public interface PersonControllerAsync {
	void getPersons(AsyncCallback<List<Person>> callback);

	void save(EntityInfo<Person> info, String name, String vorname, String strasse, String plz, String ort,
			Date geburtsdatum, AsyncCallback<Void> callback);

	void findOrCreate(EntityInfo<Person> info, AsyncCallback<Person> callback);
}
