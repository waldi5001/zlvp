package de.zlvp.ui.gruppe;

import static de.zlvp.ui.person.AbstractPersonGrid.Role.Leiter;
import static de.zlvp.ui.person.AbstractPersonGrid.Role.Teilnehmer;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.GridDropTarget;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent.BeforeShowContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import de.zlvp.control.GruppeController;
import de.zlvp.control.GruppeControllerAsync;
import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.Gruppe;
import de.zlvp.model.Leiter;
import de.zlvp.model.Person;
import de.zlvp.model.Teilnehmer;
import de.zlvp.ui.bus.ReloadTreeEvent;
import de.zlvp.ui.bus.ZlvpEventBus;
import de.zlvp.ui.person.AbstractPersonGrid;

public class Mitglieder extends AbstractPersonGrid {

	private static final GruppeControllerAsync controller = GWT.create(GruppeController.class);
	private final ColumnConfig<PersonUi, Role> role = new ColumnConfig<PersonUi, Role>(properties.role(), 100, "Rolle");
	private Gruppe gruppe;

	@Override
	public Widget asWidget() {
		Widget widget = super.asWidget();

		grid.addBeforeShowContextMenuHandler(new BeforeShowContextMenuHandler() {

			@Override
			public void onBeforeShowContextMenu(BeforeShowContextMenuEvent event) {
				event.getMenu().clear();

				final PersonUi selectedItem = grid.getSelectionModel().getSelectedItem();
				if (selectedItem != null) {
					MenuItem item = new MenuItem();
					item.setText("Zuordnung entfernen");
					item.addSelectionHandler(new SelectionHandler<Item>() {
						@Override
						public void onSelection(SelectionEvent<Item> event) {

							if (selectedItem.getRole() == Leiter) {
								Iterator<Leiter> it = gruppe.getLeiter().iterator();
								while (it.hasNext()) {
									if (selectedItem.getId() == it.next().getPerson().getId()) {
										it.remove();
									}
								}
							}

							if (selectedItem.getRole() == Teilnehmer) {
								Iterator<Teilnehmer> it = gruppe.getTeilnehmer().iterator();
								while (it.hasNext()) {
									if (selectedItem.getId() == it.next().getPerson().getId()) {
										it.remove();
									}
								}
							}

							controller.zuordnen(EntityInfo.of(gruppe), EntityInfo.ofLeiter(gruppe.getLeiter()),
									EntityInfo.ofTeilnehmer(gruppe.getTeilnehmer()), new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(Void result) {
											ZlvpEventBus.get().fireEvent(new ReloadTreeEvent());
										}
									});
						}
					});
					event.getMenu().add(item);
				}
			}
		});

		return widget;
	}

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

	@Override
	protected void initDnD() {
		GridDropTarget<PersonUi> gridDropTarget = new GridDropTarget<PersonUi>(grid);
		gridDropTarget.setGroup("gruppe");
		gridDropTarget.setOperation(Operation.COPY);
		gridDropTarget.addDropHandler(new DndDropHandler() {

			@Override
			public void onDrop(final DndDropEvent event) {
				@SuppressWarnings("unchecked")
				final List<PersonUi> persons = (List<PersonUi>) event.getData();

				final Radio leiter = new Radio();
				leiter.setBoxLabel("Leiter");

				final Radio teilnehmer = new Radio();
				teilnehmer.setBoxLabel("Teilnehmer");

				ToggleGroup group = new ToggleGroup();
				group.add(leiter);
				group.add(teilnehmer);

				VerticalPanel hp = new VerticalPanel();
				hp.add(leiter);
				hp.add(teilnehmer);

				final MessageBox mb = new MessageBox("Bitte wählen", "");
				mb.add(hp);

				mb.show();

				mb.addHideHandler(new HideHandler() {

					@Override
					public void onHide(HideEvent event) {
						if (mb.getHideButton() == mb.getButtonById(PredefinedButton.OK.name())) {
							if (leiter.getValue() || teilnehmer.getValue()) {
								Collection<EntityInfo<Person>> ofLeiter = EntityInfo.ofLeiter(gruppe.getLeiter());
								Collection<EntityInfo<Person>> ofTeilnehmer = EntityInfo.ofTeilnehmer(gruppe
										.getTeilnehmer());

								if (leiter.getValue()) {
									for (PersonUi pUi : persons) {
										ofLeiter.add(EntityInfo.of(pUi.getPerson()));
									}
								}
								if (teilnehmer.getValue()) {
									for (PersonUi pUi : persons) {
										ofTeilnehmer.add(EntityInfo.of(pUi.getPerson()));
									}
								}

								controller.zuordnen(EntityInfo.of(gruppe), ofLeiter, ofTeilnehmer,
										new AsyncCallback<Void>() {
											@Override
											public void onFailure(Throwable caught) {
												System.out.println(caught);
											}

											@Override
											public void onSuccess(Void result) {
												ZlvpEventBus.get().fireEvent(new ReloadTreeEvent());
											}
										});
							} else {
								ZlvpEventBus.get().fireEvent(new ReloadTreeEvent());
							}
						} else {
							ZlvpEventBus.get().fireEvent(new ReloadTreeEvent());
						}
					}
				});
			}
		});
	}
}
