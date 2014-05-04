package de.zlvp.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.zlvp.model.Funktion;
import de.zlvp.model.Gruppe;
import de.zlvp.model.Jahr;
import de.zlvp.model.Lager;
import de.zlvp.model.Leiter;
import de.zlvp.model.Person;
import de.zlvp.model.Stab;
import de.zlvp.model.Teilnehmer;

public class TestdataControllerImpl implements TestdataController {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void makeTestdata() {
		Jahr j = new Jahr();
		j.setBezeichnung("sfdkdjsdhfkj");

		Lager l = new Lager();
		l.setBezeichnung("dfsdfsdf");

		j.getLager().add(l);

		Stab s = new Stab();
		s.setFunktion(Funktion.Fahrer);

		Person p = new Person();
		p.setName("Person 0");

		Person p1 = new Person();
		p1.setName("Kerjke");
		p1.setVorname("Vorname");
		p1.setStrasse("Strasse");
		p1.setPlz("78199");
		p1.setOrt("Mistelbrunn");
		p1.setGeburtsdatum(new Date());

		Person p2 = new Person();
		p2.setName("Kerjke");

		Person p3 = new Person();
		p3.setName("Kerjke");

		Person p4 = new Person();
		p4.setName("Kerjke");

		Gruppe g = new Gruppe();
		g.setBezeichnung("Hallo");
		l.getGruppe().add(g);

		Leiter l1 = new Leiter();
		Leiter l2 = new Leiter();

		l1.setPerson(p1);
		l1.setGruppe(g);

		l2.setPerson(p2);
		l2.setGruppe(g);

		g.getLeiter().add(l1);
		g.getLeiter().add(l2);

		Teilnehmer t1 = new Teilnehmer();
		Teilnehmer t2 = new Teilnehmer();

		t1.setPerson(p3);
		t2.setPerson(p4);

		g.getTeilnehmer().add(t1);
		g.getTeilnehmer().add(t2);

		s.setPerson(p);

		l.getStab().add(s);

		for (int i = 0; i < 300; i++) {
			Person toAdd = new Person();
			toAdd.setName("ToAdd" + i);
			em.persist(toAdd);
		}

		em.persist(j);
		em.flush();
	}

}
