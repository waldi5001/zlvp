package de.zlvp.ui.bus;

import com.google.gwt.event.shared.SimpleEventBus;

public class ZlvpEventBus extends SimpleEventBus {

	private static final ZlvpEventBus INSTANCE = new ZlvpEventBus();

	public static ZlvpEventBus get() {
		return INSTANCE;
	}

}
