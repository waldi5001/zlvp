package de.zlvp.control;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.Person;

@RemoteServiceRelativePath("rpc/PersonController")
public interface PersonController extends RemoteService {
	void save(EntityInfo<Person> info, String name, String vorname, String strasse, String plz, String ort,
			Date geburtsdatum);

	List<Person> getPersons();

	Person findOrCreate(EntityInfo<Person> info);
}
