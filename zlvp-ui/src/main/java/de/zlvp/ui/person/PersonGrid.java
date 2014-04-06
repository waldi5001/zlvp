package de.zlvp.ui.person;

import java.util.Date;
import java.util.List;

import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import de.zlvp.model.Person;

public class PersonGrid extends AbstractPersonGrid {
	private List<Person> persons;

	@Override
	protected void addFilter(GridFilters<PersonUi> filters) {
		filters.addFilter(new StringFilter<PersonUi>(properties.name()));
		filters.addFilter(new StringFilter<PersonUi>(properties.vorname()));
		filters.addFilter(new StringFilter<PersonUi>(properties.strasse()));
		filters.addFilter(new StringFilter<PersonUi>(properties.plz()));
		filters.addFilter(new StringFilter<PersonUi>(properties.ort()));
		filters.addFilter(new DateFilter<PersonUi>(properties.geburtsdatum()));
	}

	@Override
	protected void setColumnConfig(List<ColumnConfig<PersonUi, ?>> in) {
		ColumnConfig<PersonUi, Date> gebDat = new ColumnConfig<PersonUi, Date>(properties.geburtsdatum(), 100,
				"Geburtsdatum");
		gebDat.setHidden(true);
		ColumnConfig<PersonUi, String> plz = new ColumnConfig<PersonUi, String>(properties.plz(), 100, "PLZ");
		plz.setHidden(true);

		in.add(new ColumnConfig<PersonUi, String>(properties.name(), 100, "Name"));
		in.add(new ColumnConfig<PersonUi, String>(properties.vorname(), 100, "Vorname"));
		in.add(new ColumnConfig<PersonUi, String>(properties.strasse(), 100, "Strasse"));
		in.add(plz);
		in.add(new ColumnConfig<PersonUi, String>(properties.ort(), 100, "Ort"));
		in.add(gebDat);
	}

	@Override
	protected void createPersonUi(List<PersonUi> out) {
		for (Person person : persons) {
			out.add(new PersonUi(person, null));
		}
	}

	public void setPerson(List<Person> person) {
		this.persons = person;
		load();
	}
}
