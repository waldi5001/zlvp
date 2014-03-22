package de.zlvp.ui.bus;

import com.google.gwt.event.shared.GwtEvent;

public class ReloadTreeEvent extends GwtEvent<NavigationTreeEventHandler> {
	private static final GwtEvent.Type<NavigationTreeEventHandler> TYPE = new GwtEvent.Type<NavigationTreeEventHandler>();

	public static GwtEvent.Type<NavigationTreeEventHandler> getType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NavigationTreeEventHandler handler) {
		handler.reloadTree();
	}

	@Override
	public GwtEvent.Type<NavigationTreeEventHandler> getAssociatedType() {
		return getType();
	}

}