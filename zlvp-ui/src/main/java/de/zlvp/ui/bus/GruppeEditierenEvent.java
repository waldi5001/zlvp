package de.zlvp.ui.bus;

import com.google.gwt.event.shared.GwtEvent;

import de.zlvp.model.Gruppe;

public class GruppeEditierenEvent extends GwtEvent<GruppeEditierenEventHandler> {
	private static final GwtEvent.Type<GruppeEditierenEventHandler> TYPE = new GwtEvent.Type<GruppeEditierenEventHandler>();
	private final Gruppe gruppe;

	public static GwtEvent.Type<GruppeEditierenEventHandler> getType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GruppeEditierenEventHandler handler) {
		handler.editiereGruppe(this);
	}

	@Override
	public GwtEvent.Type<GruppeEditierenEventHandler> getAssociatedType() {
		return getType();
	}

	public GruppeEditierenEvent(Gruppe gruppe) {
		this.gruppe = gruppe;
	}

	public Gruppe getGruppe() {
		return gruppe;
	}

}