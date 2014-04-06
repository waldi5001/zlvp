package de.zlvp.ui.person;

import java.util.ArrayList;
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
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
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
	ListStore<PersonUi> store = new ListStore<PersonUi>(properties.key());

	@UiField
	GroupingView<PersonUi> view;

	@UiField
	Grid<PersonUi> grid;

	@UiField(provided = true)
	PagingLoader<PagingLoadConfig, PagingLoadResult<PersonUi>> loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<PersonUi>>(
			proxy);

	@UiField
	GridFilters<PersonUi> filters;

	FramedPanel panel;
	Widget createAndBindUi;

	@UiFactory
	ColumnModel<PersonUi> createColumnModel() {
		List<ColumnConfig<PersonUi, ?>> l = new ArrayList<ColumnConfig<PersonUi, ?>>();
		setColumnConfig(l);
		return new ColumnModel<PersonUi>(l);
	}

	@Override
	public Widget asWidget() {
		if (createAndBindUi == null) {
			panel = new FramedPanel();
			loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, PersonUi, PagingLoadResult<PersonUi>>(
					store));

			createAndBindUi = uiBinder.createAndBindUi(this);
			panel.setWidget(createAndBindUi);
		}
		filters.initPlugin(grid);
		addFilter(filters);

		ColumnConfig<PersonUi, ?> groupColumnConfig = createGroupColumnConfig();
		if (groupColumnConfig != null) {
			view.groupBy(groupColumnConfig);
		}

		toolBar.bind(loader);

		return createAndBindUi;
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

	protected abstract void addFilter(GridFilters<PersonUi> filters);

	protected abstract void setColumnConfig(List<ColumnConfig<PersonUi, ?>> in);

	protected abstract void createPersonUi(List<PersonUi> out);

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
