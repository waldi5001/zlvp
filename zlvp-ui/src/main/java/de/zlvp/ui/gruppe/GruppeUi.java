package de.zlvp.ui.gruppe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import de.zlvp.model.Gruppe;

public class GruppeUi implements IsWidget {

	private Gruppe gruppe;

	@UiField
	Mitglieder mitglieder;

	private static GruppeUiBinder uiBinder = GWT.create(GruppeUiBinder.class);

	interface GruppeUiBinder extends UiBinder<Widget, GruppeUi> {
	}

	public GruppeUi() {
	}

	@Override
	public Widget asWidget() {
		Widget widget = uiBinder.createAndBindUi(this);

		mitglieder.setGruppe(gruppe);

		return widget;
	}

	public void setGruppe(Gruppe g) {
		this.gruppe = g;
	}

}
