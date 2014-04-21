package de.zlvp.ui.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent.BeforeShowContextMenuHandler;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.tree.Tree;

import de.zlvp.model.Gruppe;
import de.zlvp.model.Person;
import de.zlvp.ui.ProvideTreeContextMenuItems;
import de.zlvp.ui.gruppe.EigenschaftenDialog;
import de.zlvp.ui.navigation.NavigationTree.TreeNode;
import de.zlvp.ui.person.PersonDialog;

public class TreeContextMenuHandler implements BeforeShowContextMenuHandler {
	private final Tree<TreeNode, String> tree;

	private static final Map<Class<?>, ProvideTreeContextMenuItems> REGISTRY = new HashMap<Class<?>, ProvideTreeContextMenuItems>();

	@Override
	public void onBeforeShowContextMenu(BeforeShowContextMenuEvent event) {
		Menu menu = event.getMenu();
		menu.clear();

		TreeNode selectedItem = tree.getSelectionModel().getSelectedItem();
		List<MenuItem> menuItems = REGISTRY.get(selectedItem.getParent().getClass()).getMenuItems(selectedItem);
		for (MenuItem menuItem : menuItems) {
			menu.add(menuItem);
		}

	}

	public TreeContextMenuHandler(Tree<TreeNode, String> tree) {
		this.tree = tree;
		REGISTRY.put(Gruppe.class, new EigenschaftenDialog());
		REGISTRY.put(Person.class, new PersonDialog());
	}
}
