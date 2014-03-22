package de.zlvp.ui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;

import de.zlvp.ui.bus.GruppeEditierenEvent;
import de.zlvp.ui.bus.GruppeEditierenEventHandler;
import de.zlvp.ui.bus.PersonAendernEvent;
import de.zlvp.ui.bus.PersonAendernEventHandler;
import de.zlvp.ui.bus.ZlvpEventBus;
import de.zlvp.ui.gruppe.PersonGrid;
import de.zlvp.ui.person.PersonEditor;

public class ZLVP implements EntryPoint, IsWidget {

	@UiField
	SimpleContainer centerContainer;

	@UiField
	NavigationTree tree;

	interface ZLVPUiBinder extends UiBinder<Widget, ZLVP> {
	}

	private static ZLVPUiBinder uiBinder = GWT.create(ZLVPUiBinder.class);

	@Override
	public void onModuleLoad() {
		ZlvpEventBus.get().addHandler(PersonAendernEvent.getType(), new PersonAendernEventHandler() {
			@Override
			public void personAendern(PersonAendernEvent event) {
				PersonEditor child = new PersonEditor();
				centerContainer.add(child);
				child.editPerson(event.getPerson());
			}
		});

		ZlvpEventBus.get().addHandler(GruppeEditierenEvent.getType(), new GruppeEditierenEventHandler() {
			@Override
			public void editiereGruppe(GruppeEditierenEvent event) {
				PersonGrid pg = new PersonGrid();
				pg.setGruppe(event.getGruppe());
				centerContainer.add(pg);
			}
		});

		Viewport viewport = new Viewport();
		viewport.setWidget(asWidget());
		RootPanel.get().add(viewport);
	}

	@Override
	public Widget asWidget() {
		return uiBinder.createAndBindUi(this);
	}

}
