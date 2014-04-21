package de.zlvp.ui.navigation;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.tree.TreeSelectionModel;

import de.zlvp.model.AbstractEntity;
import de.zlvp.model.Gruppe;
import de.zlvp.ui.IsTreeChildren;
import de.zlvp.ui.gruppe.GruppeTabPanel;
import de.zlvp.ui.navigation.NavigationTree.TreeNode;

public class NavigationTreeSelectionModel<T extends TreeNode> extends TreeSelectionModel<T> {

	private final SimpleContainer containerToAdd;

	private static final Map<Class<?>, IsTreeChildren> REGISTRY = new HashMap<Class<?>, IsTreeChildren>();

	public NavigationTreeSelectionModel(SimpleContainer containerToAdd) {
		this.containerToAdd = containerToAdd;
		REGISTRY.put(Gruppe.class, new GruppeTabPanel());
	}

	@Override
	protected void onSelectChange(T model, boolean select) {
		AbstractEntity parent = model.getParent();
		if (select && REGISTRY.containsKey(parent.getClass())) {
			Widget show = REGISTRY.get(parent.getClass()).show(parent);
			containerToAdd.setWidget(show);
			containerToAdd.forceLayout();
		}
	}

}
