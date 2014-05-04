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

public class GruppeControllerImpl extends AbstractController implements GruppeController {

	private GruppeDao dao;
	private PersonDao personDao;

	@Override
	public void zuordnen(EntityInfo<Gruppe> grInfo, Collection<EntityInfo<Person>> leiter,
			Collection<EntityInfo<Person>> teilnehmer) {
		Gruppe gruppe = findOrCreate(Gruppe.class, dao, grInfo);

		gruppe.getTeilnehmer().clear();
		gruppe.getLeiter().clear();

		for (Person p : personDao.findAll(EntityInfo.getIds(leiter))) {
			Leiter t = new Leiter();
			t.setPerson(p);
			t.setGruppe(gruppe);
			gruppe.getLeiter().add(t);
		}

		for (Person p : personDao.findAll(EntityInfo.getIds(teilnehmer))) {
			Teilnehmer t = new Teilnehmer();
			t.setPerson(p);
			t.setGruppe(gruppe);
			gruppe.getTeilnehmer().add(t);
		}
	}

	@Override
	public void speichern(EntityInfo<Gruppe> info, String name, String schlachtruf) {
		Gruppe gruppe = findOrCreate(Gruppe.class, dao, info);
		gruppe.setBezeichnung(name);
		gruppe.setSchlachtruf(schlachtruf);
	}

	public void setDao(GruppeDao dao) {
		this.dao = dao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

}
