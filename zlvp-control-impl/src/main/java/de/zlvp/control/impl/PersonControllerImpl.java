package de.zlvp.control.impl;

import de.zlvp.control.PersonController;
import de.zlvp.control.dao.PersonDao;
import de.zlvp.model.Person;

public class PersonControllerImpl implements PersonController {
	private PersonDao dao;

	@Override
	public Person savePerson(Person person) {
		return dao.save(person);
	}

	public void setDao(PersonDao dao) {
		this.dao = dao;
	}

}
