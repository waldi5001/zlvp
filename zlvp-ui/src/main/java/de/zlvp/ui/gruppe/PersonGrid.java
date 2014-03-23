package de.zlvp.ui.gruppe;

import static de.zlvp.ui.gruppe.PersonGrid.Role.Leiter;
import static de.zlvp.ui.gruppe.PersonGrid.Role.Teilnehmer;

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
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.dnd.core.client.GridDropTarget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import de.zlvp.model.Gruppe;
import de.zlvp.model.Leiter;
import de.zlvp.model.Person;
import de.zlvp.model.Teilnehmer;

public class PersonGrid implements IsWidget {

	private static PersonGridUiBinder uiBinder = GWT.create(PersonGridUiBinder.class);

	interface PersonGridUiBinder extends UiBinder<Widget, PersonGrid> {
	}

	public static final PersonProperties properties = GWT.create(PersonProperties.class);

	StringFilter<PersonUi> nameFilter = new StringFilter<PersonUi>(properties.name());
	StringFilter<PersonUi> vornameFilter = new StringFilter<PersonUi>(properties.vorname());
	StringFilter<PersonUi> strasseFilter = new StringFilter<PersonUi>(properties.strasse());
	StringFilter<PersonUi> plzFilter = new StringFilter<PersonUi>(properties.plz());
	StringFilter<PersonUi> ortFilter = new StringFilter<PersonUi>(properties.ort());
	DateFilter<PersonUi> gebDatFilter = new DateFilter<PersonUi>(properties.geburtsdatum());

	@UiField(provided = true)
	ColumnConfig<PersonUi, String> name = new ColumnConfig<PersonUi, String>(properties.name());
	@UiField(provided = true)
	ColumnConfig<PersonUi, String> vorname = new ColumnConfig<PersonUi, String>(properties.vorname());
	@UiField(provided = true)
	ColumnConfig<PersonUi, String> strasse = new ColumnConfig<PersonUi, String>(properties.strasse());
	@UiField(provided = true)
	ColumnConfig<PersonUi, String> plz = new ColumnConfig<PersonUi, String>(properties.plz());
	@UiField(provided = true)
	ColumnConfig<PersonUi, String> ort = new ColumnConfig<PersonUi, String>(properties.ort());
	@UiField(provided = true)
	ColumnConfig<PersonUi, Date> geburtsdatum = new ColumnConfig<PersonUi, Date>(properties.geburtsdatum());
	@UiField(provided = true)
	ColumnConfig<PersonUi, Role> role = new ColumnConfig<PersonUi, Role>(properties.role());

	@UiField
	PagingToolBar toolBar;

	@UiField
	ColumnModel<Person> cm;

	@UiField(provided = true)
	ListStore<PersonUi> store = new ListStore<PersonUi>(properties.key());

	@UiField
	GroupingView<PersonUi> view;

	@UiField
	Grid<PersonUi> grid;

	@UiField(provided = true)
	PagingLoader<PagingLoadConfig, PagingLoadResult<PersonUi>> loader;

	@UiField
	GridFilters<PersonUi> filters;

	FramedPanel panel;

	@UiFactory
	ColumnModel<PersonUi> createColumnModel() {
		List<ColumnConfig<PersonUi, ?>> l = new ArrayList<ColumnConfig<PersonUi, ?>>();

		l.add(name);
		l.add(vorname);
		l.add(strasse);
		l.add(plz);
		l.add(ort);
		l.add(geburtsdatum);
		l.add(role);

		return new ColumnModel<PersonUi>(l);
	}

	@Override
	public Widget asWidget() {
		if (panel == null) {
			name.setHeader("Nachname");
			vorname.setHeader("Vorname");
			strasse.setHeader("Strasse");
			plz.setHeader("PLZ");
			ort.setHeader("Ort");
			role.setHeader("Rolle");
			geburtsdatum.setHeader("Geburtsdatum");

			RpcProxy<PagingLoadConfig, PagingLoadResult<PersonUi>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<PersonUi>>() {
				@Override
				public void load(final PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<PersonUi>> callback) {
					callback.onSuccess(new PagingLoadResult<PersonGrid.PersonUi>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<PersonUi> getData() {
							return null;
						}

						@Override
						public int getOffset() {
							return 0;
						}

						@Override
						public int getTotalLength() {
							return 0;
						}

						@Override
						public void setOffset(int offset) {
						}

						@Override
						public void setTotalLength(int totalLength) {
						}
					});

				}
			};
			loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<PersonUi>>(proxy);
			loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, PersonUi, PagingLoadResult<PersonUi>>(
					store));

			panel = new FramedPanel();
		}

		Widget widget = uiBinder.createAndBindUi(this);

		toolBar.bind(loader);
		filters.initPlugin(grid);
		filters.addFilter(nameFilter);
		filters.addFilter(vornameFilter);
		filters.addFilter(strasseFilter);
		filters.addFilter(plzFilter);
		filters.addFilter(ortFilter);
		filters.addFilter(gebDatFilter);

		GridDragSource<PersonUi> dragSource = new GridDragSource<PersonUi>(grid);
		dragSource.setGroup("top");

		GridDropTarget<PersonUi> gridDropTarget = new GridDropTarget<PersonUi>(grid);
		gridDropTarget.setGroup("top");
		gridDropTarget.setOperation(Operation.COPY);
		gridDropTarget.addDropHandler(new DndDropHandler() {
			@Override
			public void onDrop(DndDropEvent event) {
				System.out.println(event.getData());
			}
		});

		panel.setWidget(widget);

		return panel;
	}

	public static enum Role {
		Stab, Leiter, Teilnehmer;
	}

	public static class PersonUi extends Person {
		private final Role role;
		private static final long serialVersionUID = 1L;

		public PersonUi(Person p, Role r) {
			setName(p.getName());
			setVorname(p.getVorname());
			setStrasse(p.getStrasse());
			setPlz(p.getPlz());
			setOrt(p.getOrt());
			setGeburtsdatum(p.getGeburtsdatum());
			role = r;
		}

		public Role getRole() {
			return role;
		}
	}

	public void setGruppe(Gruppe gruppe) {
		for (Teilnehmer teilnehmer : gruppe.getTeilnehmer()) {
			store.add(new PersonUi(teilnehmer.getPerson(), Teilnehmer));
		}
		for (Leiter leiter : gruppe.getLeiter()) {
			store.add(new PersonUi(leiter.getPerson(), Leiter));
		}
		view.groupBy(role);
	}

	public void setPerson(List<Person> persons) {
		for (Person person : persons) {
			store.add(new PersonUi(person, null));
		}
	}
}
