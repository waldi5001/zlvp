package de.zlvp.control;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.zlvp.model.Person;

public interface PersonControllerAsync {
	void savePerson(Person person, AsyncCallback<Person> callback);

	void getPersons(AsyncCallback<List<Person>> callback);
}
