package de.zlvp.ui.gruppe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import de.zlvp.model.Gruppe;
import de.zlvp.model.Person;
import de.zlvp.model.Teilnehmer;

public class PersonGrid implements IsWidget {

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
	PagingToolBar toolBar;

	@UiField
	ColumnModel<Person> cm;

	@UiField(provided = true)
	ListStore<Person> store = new ListStore<Person>(properties.key());

	@UiField
	GroupingView<Person> view;

	@UiField
	Grid<Person> grid;

	@UiField(provided = true)
	PagingLoader<PagingLoadConfig, PagingLoadResult<Person>> loader;

	public PersonGrid() {
		name.setHeader("Nachname");
		vorname.setHeader("Vorname");
		strasse.setHeader("Strasse");
		plz.setHeader("PLZ");
		ort.setHeader("Ort");
		geburtsdatum.setHeader("Geburtsdatum");

		RpcProxy<PagingLoadConfig, PagingLoadResult<Person>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<Person>>() {
			@Override
			public void load(final PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<Person>> callback) {
				callback.onSuccess(new PagingLoadResult<Person>() {

					private static final long serialVersionUID = 1L;

					@Override
					public List<Person> getData() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public int getOffset() {
						// TODO Auto-generated method stub
						return loadConfig.getOffset();
					}

					@Override
					public int getTotalLength() {
						// TODO Auto-generated method stub
						return 0;
					}

					@Override
					public void setOffset(int offset) {
						// TODO Auto-generated method stub

					}

					@Override
					public void setTotalLength(int totalLength) {
						// TODO Auto-generated method stub

					}
				});
			}
		};
		loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<Person>>(proxy);
		loader.setRemoteSort(true);
		loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, Person, PagingLoadResult<Person>>(store));
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

	@Override
	public Widget asWidget() {
		Widget widget = uiBinder.createAndBindUi(this);
		toolBar.bind(loader);
		return widget;
	}
}
