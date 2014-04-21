package de.zlvp.control;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.Gruppe;
import de.zlvp.model.Person;

public interface GruppeControllerAsync {

	void zuordnen(EntityInfo<Gruppe> gruppe, Collection<EntityInfo<Person>> leiter,
			Collection<EntityInfo<Person>> teilnehmer, AsyncCallback<Void> callback);

	void speichern(EntityInfo<Gruppe> gruppe, String name, String schlachtruf, AsyncCallback<Void> callback);

}
