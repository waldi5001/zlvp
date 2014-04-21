package de.zlvp.control.impl;

import java.util.Collection;

import de.zlvp.control.GruppeController;
import de.zlvp.control.dao.GruppeDao;
import de.zlvp.control.dao.PersonDao;
import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.Gruppe;
import de.zlvp.model.Leiter;
import de.zlvp.model.Person;
import de.zlvp.model.Teilnehmer;

public class GruppeControllerImpl implements GruppeController {

	private GruppeDao dao;
	private PersonDao personDao;

	public void setDao(GruppeDao dao) {
		this.dao = dao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	public void zuordnen(EntityInfo<Gruppe> grInfo, Collection<EntityInfo<Person>> leiter,
			Collection<EntityInfo<Person>> teilnehmer) {
		Gruppe gruppe = dao.findByIdAndVersion(grInfo.getId(), grInfo.getVersion());

		gruppe.getTeilnehmer().clear();
		gruppe.getLeiter().clear();

		for (EntityInfo<Person> eiLeiter : leiter) {
			Person person = personDao.findByIdAndVersion(eiLeiter.getId(), eiLeiter.getVersion());
			Leiter t = new Leiter();
			t.setPerson(person);
			t.setGruppe(gruppe);
			gruppe.getLeiter().add(t);
		}

		for (EntityInfo<Person> eiTeilnehmer : teilnehmer) {
			Person person = personDao.findByIdAndVersion(eiTeilnehmer.getId(), eiTeilnehmer.getVersion());
			Teilnehmer t = new Teilnehmer();
			t.setPerson(person);
			t.setGruppe(gruppe);
			gruppe.getTeilnehmer().add(t);
		}
	}

	@Override
	public void speichern(EntityInfo<Gruppe> grInfo, String name, String schlachtruf) {
		Gruppe gruppe = dao.findByIdAndVersion(grInfo.getId(), grInfo.getVersion());
		gruppe.setBezeichnung(name);
		gruppe.setSchlachtruf(schlachtruf);
	}

}
