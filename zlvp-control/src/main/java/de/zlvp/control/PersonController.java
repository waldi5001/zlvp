package de.zlvp.control;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.zlvp.model.Person;

@RemoteServiceRelativePath("rpc/PersonController")
public interface PersonController extends RemoteService {
	Person savePerson(Person person);

	List<Person> getPersons();
}
