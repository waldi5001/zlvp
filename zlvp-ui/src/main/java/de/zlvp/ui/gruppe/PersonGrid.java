package de.zlvp.ui.gruppe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;

import de.zlvp.model.Gruppe;
import de.zlvp.model.Person;
import de.zlvp.model.Teilnehmer;

public class PersonGrid extends Composite {

	private static PersonGridUiBinder uiBinder = GWT.create(PersonGridUiBinder.class);

	interface PersonGridUiBinder extends UiBinder<Widget, PersonGrid> {
	}

	public static final PersonProperties properties = GWT.create(PersonProperties.class);

	@UiField(provided = true)
	ColumnConfig<Person, String> name = new ColumnConfig<Person, String>(properties.name());
	@UiField(provided = true)
	ColumnConfig<Person, String> vorname = new ColumnConfig<Person, String>(properties.vorname());
	@UiField(provided = true)
	ColumnConfig<Person, String> strasse = new ColumnConfig<Person, String>(properties.strasse());
	@UiField(provided = true)
	ColumnConfig<Person, String> plz = new ColumnConfig<Person, String>(properties.plz());
	@UiField(provided = true)
	ColumnConfig<Person, String> ort = new ColumnConfig<Person, String>(properties.ort());
	@UiField(provided = true)
	ColumnConfig<Person, Date> geburtsdatum = new ColumnConfig<Person, Date>(properties.geburtsdatum());

	@UiField
	ColumnModel<Person> cm;

	@UiField(provided = true)
	ListStore<Person> store = new ListStore<Person>(properties.key());

	@UiField
	GroupingView<Person> view;

	@UiField
	Grid<Person> grid;

	public PersonGrid() {
		name.setHeader("Nachname");
		vorname.setHeader("Vorname");
		strasse.setHeader("Strasse");
		plz.setHeader("PLZ");
		ort.setHeader("Ort");
		geburtsdatum.setHeader("Geburtsdatum");
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setGruppe(Gruppe gruppe) {
		for (Teilnehmer teilnehmer : gruppe.getTeilnehmer()) {
			store.add(teilnehmer.getPerson());
		}
	}

	@UiFactory
	ColumnModel<Person> createColumnModel() {
		List<ColumnConfig<Person, ?>> l = new ArrayList<ColumnConfig<Person, ?>>();

		l.add(name);
		l.add(vorname);
		l.add(strasse);
		l.add(plz);
		l.add(ort);
		l.add(geburtsdatum);

		return new ColumnModel<Person>(l);
	}
}
