package de.zlvp.ui.bus;

import com.google.gwt.event.shared.GwtEvent;

public class ReloadTreeEvent extends GwtEvent<ReloadTreeEventHandler> {
	private static final GwtEvent.Type<ReloadTreeEventHandler> TYPE = new GwtEvent.Type<ReloadTreeEventHandler>();

	public static GwtEvent.Type<ReloadTreeEventHandler> getType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReloadTreeEventHandler handler) {
		handler.reloadTree();
	}

	@Override
	public GwtEvent.Type<ReloadTreeEventHandler> getAssociatedType() {
		return getType();
	}

}