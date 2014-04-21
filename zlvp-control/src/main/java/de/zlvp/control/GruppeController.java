package de.zlvp.control;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.Gruppe;
import de.zlvp.model.Person;

@RemoteServiceRelativePath("rpc/GruppeController")
public interface GruppeController extends RemoteService {

	void zuordnen(EntityInfo<Gruppe> gruppe, Collection<EntityInfo<Person>> leiter,
			Collection<EntityInfo<Person>> teilnehmer);

	void speichern(EntityInfo<Gruppe> gruppe, String name, String schlachtruf);

}
