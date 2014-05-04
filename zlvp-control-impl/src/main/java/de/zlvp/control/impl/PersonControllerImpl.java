package de.zlvp.control.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.zlvp.control.PersonController;
import de.zlvp.control.dao.PersonDao;
import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.Person;

public class PersonControllerImpl extends AbstractController implements PersonController {
	private PersonDao dao;

	@Override
	public List<Person> getPersons() {
		List<Person> list = new ArrayList<Person>();
		for (Person person : dao.findAll()) {
			list.add(person);
		}
		return list;
	}

	@Override
	public void save(EntityInfo<Person> info, String name, String vorname, String strasse, String plz, String ort,
			Date geburtsdatum) {
		Person p = findOrCreate(info);
		p.setName(name);
		p.setVorname(vorname);
		p.setStrasse(strasse);
		p.setPlz(plz);
		p.setOrt(ort);
		p.setGeburtsdatum(geburtsdatum);
	}

	public void setDao(PersonDao dao) {
		this.dao = dao;
	}

	@Override
	public Person findOrCreate(EntityInfo<Person> info) {
		return findOrCreate(Person.class, dao, info);
	}
}
