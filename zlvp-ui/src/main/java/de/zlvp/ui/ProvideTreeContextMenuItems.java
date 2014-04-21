package de.zlvp.ui;

import java.util.List;

import com.sencha.gxt.widget.core.client.menu.MenuItem;

public interface ProvideTreeContextMenuItems {
	List<MenuItem> getMenuItems(Object selectedItem);
}