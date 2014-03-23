package de.zlvp.control.impl;

import java.util.ArrayList;
import java.util.List;

import de.zlvp.control.PersonController;
import de.zlvp.control.dao.PersonDao;
import de.zlvp.model.Person;

public class PersonControllerImpl implements PersonController {
	private PersonDao dao;

	@Override
	public Person savePerson(Person person) {
		return dao.save(person);
	}

	@Override
	public List<Person> getPersons() {
		List<Person> list = new ArrayList<Person>();
		for (Person person : dao.findAll()) {
			list.add(person);
		}
		return list;
	}

	public void setDao(PersonDao dao) {
		this.dao = dao;
	}

}
