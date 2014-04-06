package de.zlvp.ui.gruppe;

import static de.zlvp.ui.person.AbstractPersonGrid.Role.Leiter;
import static de.zlvp.ui.person.AbstractPersonGrid.Role.Teilnehmer;

import java.util.Date;
import java.util.List;

import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import de.zlvp.model.Gruppe;
import de.zlvp.model.Leiter;
import de.zlvp.model.Teilnehmer;
import de.zlvp.ui.person.AbstractPersonGrid;

public class Mitglieder extends AbstractPersonGrid {

	private final ColumnConfig<PersonUi, Role> role = new ColumnConfig<PersonUi, Role>(properties.role(), 100, "Rolle");
	private Gruppe gruppe;

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
		in.add(new ColumnConfig<PersonUi, String>(properties.name(), 100, "Name"));
		in.add(new ColumnConfig<PersonUi, String>(properties.vorname(), 100, "Vorname"));
		in.add(new ColumnConfig<PersonUi, String>(properties.strasse(), 100, "Strasse"));
		in.add(new ColumnConfig<PersonUi, String>(properties.plz(), 100, "PLZ"));
		in.add(new ColumnConfig<PersonUi, String>(properties.ort(), 100, "Ort"));
		in.add(new ColumnConfig<PersonUi, Date>(properties.geburtsdatum(), 100, "Geburtsdatum"));
		in.add(role);
	}

	@Override
	protected ColumnConfig<PersonUi, ?> createGroupColumnConfig() {
		return role;
	}

	@Override
	protected void createPersonUi(List<PersonUi> out) {
		for (Teilnehmer teilnehmer : gruppe.getTeilnehmer()) {
			out.add(new PersonUi(teilnehmer.getPerson(), Teilnehmer));
		}
		for (Leiter leiter : gruppe.getLeiter()) {
			out.add(new PersonUi(leiter.getPerson(), Leiter));
		}
	}

	public void setGruppe(Gruppe g) {
		this.gruppe = g;
		load();
	}

}
