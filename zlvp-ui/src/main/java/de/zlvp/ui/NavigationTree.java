package de.zlvp.ui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.TreeSelectionModel;

import de.zlvp.control.ZlvpController;
import de.zlvp.control.ZlvpControllerAsync;
import de.zlvp.model.BaseEntity;
import de.zlvp.model.Gruppe;
import de.zlvp.model.Jahr;
import de.zlvp.model.Lager;
import de.zlvp.model.Leiter;
import de.zlvp.model.Person;
import de.zlvp.model.Stab;
import de.zlvp.model.Teilnehmer;
import de.zlvp.ui.bus.GruppeEditierenEvent;
import de.zlvp.ui.bus.NavigationTreeEventHandler;
import de.zlvp.ui.bus.PersonAendernEvent;
import de.zlvp.ui.bus.ReloadTreeEvent;
import de.zlvp.ui.bus.ZlvpEventBus;

public class NavigationTree extends ContentPanel {

	private static final String LABEL_TEILNEHMER = "Teilnehmer";
	private static final String LABEL_LEITER = "Leiter";
	private static final String LABEL_STAB = "Stab";

	public ZlvpControllerAsync controller = GWT.create(ZlvpController.class);

	interface NavigationTreeUiBinder extends UiBinder<Widget, NavigationTree> {
	}

	private static NavigationTreeUiBinder uiBinder = GWT.create(NavigationTreeUiBinder.class);

	class KeyProvider implements ModelKeyProvider<Object> {
		@Override
		public String getKey(Object item) {
			if (item instanceof String) {
				return (String) item;
			} else {
				return item.toString();
			}
		}
	}

	@UiFactory
	public ValueProvider<Object, String> createValueProvider() {
		return new ValueProvider<Object, String>() {
			@Override
			public String getValue(Object object) {
				if (object instanceof BaseEntity) {
					return ((BaseEntity) object).getBezeichnung();
				} else if (object instanceof Person) {
					Person p = (Person) object;
					return p.getName() + ", " + p.getVorname();
				} else if (object instanceof String) {
					return (String) object;
				} else {
					return "NULL";
				}
			}

			@Override
			public void setValue(Object object, String value) {
			}

			@Override
			public String getPath() {
				return null;
			}
		};
	}

	@UiField(provided = true)
	TreeStore<Object> store = new TreeStore<Object>(new KeyProvider());

	@UiField(provided = true)
	TreeSelectionModel<Object> selectionModel = new TreeSelectionModel<Object>() {
		@Override
		protected void onSelectChange(Object model, boolean select) {
			if (select) {
				if (model instanceof Person) {
					ZlvpEventBus.get().fireEvent(new PersonAendernEvent((Person) model));
				} else if (model instanceof Gruppe) {
					ZlvpEventBus.get().fireEvent(new GruppeEditierenEvent((Gruppe) model));
				}
			}
		}
	};

	@UiField
	Tree<Object, String> tree;

	public NavigationTree() {
		ZlvpEventBus.get().addHandler(ReloadTreeEvent.getType(), new NavigationTreeEventHandler() {
			@Override
			public void reloadTree() {
				reload();
			}
		});
		add(uiBinder.createAndBindUi(this));
		reload();
	}

	public void reload() {
		final Object selectedItem = tree.getSelectionModel().getSelectedItem();
		controller.getJahre(new AsyncCallback<List<Jahr>>() {
			@Override
			public void onSuccess(List<Jahr> result) {
				Jahr j = result.get(0);
				addToStore(j);
				if (selectedItem != null) {
					tree.setExpanded(selectedItem, true, true);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("FAIL");
			}
		});
	}

	private void addToStore(Jahr j) {
		store.clear();
		store.add(j);
		for (Lager lager : j.getLager()) {
			store.add(j, lager);

			String s = LABEL_STAB;
			if (!lager.getStab().isEmpty()) {
				store.add(lager, s);
			}
			for (Stab stab : lager.getStab()) {
				store.add(s, stab.getPerson());
			}

			for (Gruppe gruppe : lager.getGruppe()) {
				store.add(lager, gruppe);

				String le = LABEL_LEITER;
				if (!gruppe.getLeiter().isEmpty()) {
					store.add(gruppe, le);
				}

				String t = LABEL_TEILNEHMER;
				if (!gruppe.getTeilnehmer().isEmpty()) {
					store.add(gruppe, t);
				}

				for (Leiter leiter : gruppe.getLeiter()) {
					store.add(le, leiter.getPerson());
				}
				for (Teilnehmer teilnehmer : gruppe.getTeilnehmer()) {
					store.add(t, teilnehmer.getPerson());
				}
			}
		}
	}
}
