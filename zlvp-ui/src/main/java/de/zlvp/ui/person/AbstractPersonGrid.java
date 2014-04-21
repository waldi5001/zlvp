package de.zlvp.ui.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.MemoryProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import de.zlvp.model.Person;

public abstract class AbstractPersonGrid implements IsWidget {
	private final MemoryProxy<PagingLoadConfig, PagingLoadResult<PersonUi>> proxy = new MemoryProxy<PagingLoadConfig, PagingLoadResult<PersonUi>>(
			null);

	private static PersonGridUiBinder uiBinder = GWT.create(PersonGridUiBinder.class);

	interface PersonGridUiBinder extends UiBinder<Widget, AbstractPersonGrid> {
	}

	public static final PersonProperties properties = GWT.create(PersonProperties.class);

	@UiField
	PagingToolBar toolBar;

	@UiField
	ColumnModel<Person> cm;

	@UiField(provided = true)
	protected ListStore<PersonUi> store = new ListStore<PersonUi>(properties.key());

	@UiField
	GroupingView<PersonUi> view;

	@UiField
	protected Grid<PersonUi> grid;

	@UiField(provided = true)
	protected PagingLoader<PagingLoadConfig, PagingLoadResult<PersonUi>> loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<PersonUi>>(
			proxy);

	@UiField
	GridFilters<PersonUi> filters;

	Widget widget;

	@UiFactory
	ColumnModel<PersonUi> createColumnModel() {
		List<ColumnConfig<PersonUi, ?>> l = new ArrayList<ColumnConfig<PersonUi, ?>>();
		setColumnConfig(l);
		return new ColumnModel<PersonUi>(l);
	}

	@Override
	public Widget asWidget() {
		if (widget == null) {
			loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, PersonUi, PagingLoadResult<PersonUi>>(
					store));
			widget = uiBinder.createAndBindUi(this);
		}
		filters.initPlugin(grid);
		addFilter(filters);

		ColumnConfig<PersonUi, ?> groupColumnConfig = createGroupColumnConfig();
		if (groupColumnConfig != null) {
			view.groupBy(groupColumnConfig);
		}

		grid.setContextMenu(new Menu());

		toolBar.bind(loader);

		initDnD();

		return widget;
	}

	public static enum Role {
		Stab, Leiter, Teilnehmer;
	}

	public static class PersonUi implements Serializable {
		private static final long serialVersionUID = 1L;

		private final Person person;
		private final Role role;

		public PersonUi() {
			this(new Person(), Role.Teilnehmer);
		}

		public PersonUi(Person p, Role r) {
			role = r;
			person = p;
		}

		public Role getRole() {
			return role;
		}

		public long getId() {
			return person.getId();
		}

		public void setId(long id) {
			person.setId(id);
		}

		public String getName() {
			return person.getName();
		}

		public long getVersion() {
			return person.getVersion();
		}

		public void setName(String name) {
			person.setName(name);
		}

		public void setVersion(long version) {
			person.setVersion(version);
		}

		public String getVorname() {
			return person.getVorname();
		}

		public void setVorname(String vorname) {
			person.setVorname(vorname);
		}

		public String getStrasse() {
			return person.getStrasse();
		}

		public void setStrasse(String strasse) {
			person.setStrasse(strasse);
		}

		public String getPlz() {
			return person.getPlz();
		}

		public void setPlz(String plz) {
			person.setPlz(plz);
		}

		public String getOrt() {
			return person.getOrt();
		}

		public void setOrt(String ort) {
			person.setOrt(ort);
		}

		public Date getGeburtsdatum() {
			return person.getGeburtsdatum();
		}

		public void setGeburtsdatum(Date geburtsdatum) {
			person.setGeburtsdatum(geburtsdatum);
		}

		public Person getPerson() {
			return person;
		}

	}

	protected abstract void addFilter(GridFilters<PersonUi> filters);

	protected abstract void setColumnConfig(List<ColumnConfig<PersonUi, ?>> in);

	protected abstract void createPersonUi(List<PersonUi> out);

	protected abstract void initDnD();

	protected ColumnConfig<PersonUi, ?> createGroupColumnConfig() {
		return null;
	}

	protected void load() {
		List<PersonUi> out = new ArrayList<PersonUi>();
		createPersonUi(out);
		proxy.setData(new PagingLoadResultBean<PersonUi>(out, out.size(), 0));
		loader.load();
	}

}
