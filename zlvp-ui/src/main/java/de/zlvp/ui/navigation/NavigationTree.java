package de.zlvp.ui.navigation;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.event.StoreDataChangeEvent;
import com.sencha.gxt.data.shared.event.StoreDataChangeEvent.StoreDataChangeHandler;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.tree.Tree;

import de.zlvp.control.ZlvpControllerAsync;
import de.zlvp.model.AbstractEntity;
import de.zlvp.model.BaseEntity;
import de.zlvp.model.Gruppe;
import de.zlvp.model.Jahr;
import de.zlvp.model.Lager;
import de.zlvp.model.Leiter;
import de.zlvp.model.Person;
import de.zlvp.model.Teilnehmer;
import de.zlvp.ui.ControllerFactory;
import de.zlvp.ui.bus.ReloadTreeEvent;
import de.zlvp.ui.bus.ReloadTreeEventHandler;
import de.zlvp.ui.bus.ZlvpEventBus;
import de.zlvp.ui.handler.TreeContextMenuHandler;

public class NavigationTree implements IsWidget {

	public static final ZlvpControllerAsync controller = ControllerFactory.getZlvpController();

	interface NavigationTreeUiBinder extends UiBinder<Widget, NavigationTree> {
	}

	private static NavigationTreeUiBinder uiBinder = GWT.create(NavigationTreeUiBinder.class);

	final RpcProxy<TreeNode, List<TreeNode>> p = new RpcProxy<NavigationTree.TreeNode, List<TreeNode>>() {
		@Override
		public void load(final TreeNode loadConfig, final AsyncCallback<List<TreeNode>> callback) {
			if (loadConfig == null) {
				controller.getJahre(new AsyncCallback<List<Jahr>>() {
					@Override
					public void onFailure(Throwable caught) {
						System.out.println(caught);
					}

					@Override
					public void onSuccess(List<Jahr> result) {
						callback.onSuccess(new TreeModel(result).getNodes());
					}
				});
			} else {
				callback.onSuccess(loadConfig.getChildren());
			}
		}
	};

	private final TreeLoader<TreeNode> loader = new TreeLoader<TreeNode>(p) {
		@Override
		public boolean hasChildren(TreeNode parent) {
			return !parent.getChildren().isEmpty();
		};
	};

	class KeyProvider implements ModelKeyProvider<Object> {
		@Override
		public String getKey(Object item) {
			TreeNode node = (TreeNode) item;
			return node.getKey();
		}
	}

	@UiFactory
	public ValueProvider<Object, String> createValueProvider() {
		return new ValueProvider<Object, String>() {
			@Override
			public String getValue(Object object) {
				return ((TreeNode) object).getLabel();
			}

			@Override
			public void setValue(Object object, String value) {
			}

			@Override
			public String getPath() {
				return "bezeichnung";
			}
		};
	}

	@UiField(provided = true)
	TreeStore<TreeNode> store = new TreeStore<TreeNode>(new KeyProvider());

	@UiField
	Tree<TreeNode, String> tree;

	public NavigationTree() {
		uiBinder.createAndBindUi(this);
		tree.setLoader(loader);

		tree.setContextMenu(new Menu());
		tree.addBeforeShowContextMenuHandler(new TreeContextMenuHandler(tree));
		tree.setAutoLoad(true);

		loader.addLoadHandler(new ChildTreeStoreBinding<TreeNode>(store));

		store.addStoreDataChangeHandler(new StoreDataChangeHandler<NavigationTree.TreeNode>() {

			@Override
			public void onDataChange(StoreDataChangeEvent<TreeNode> event) {
				final TreeNode selectedItem = tree.getSelectionModel().getSelectedItem();
				if (selectedItem != null) {
					if (event.getParent() != null && selectedItem.getKey().equals(event.getParent().getKey())) {
						tree.getSelectionModel().select(event.getParent(), false);
					}
					if (event.getParent() == null) {
						tree.setExpanded(selectedItem, true);
					}
				}
			}
		});

		ZlvpEventBus.get().addHandler(ReloadTreeEvent.getType(), new ReloadTreeEventHandler() {
			@Override
			public void reloadTree() {
				reload();
			}
		});
	}

	public void reload() {
		loader.load(null);
	}

	@Override
	public Widget asWidget() {
		return tree;
	}

	public void setSelectionModel(NavigationTreeSelectionModel<TreeNode> selectionModel) {
		selectionModel.bindTree(tree);
		tree.setSelectionModel(selectionModel);
	}

	private static class TreeModel {
		private final List<TreeNode> nodes = new ArrayList<TreeNode>();

		public TreeModel(List<Jahr> j) {
			for (Jahr jahr : j) {
				nodes.add(new TreeNode(jahr));
			}
		}

		public List<TreeNode> getNodes() {
			return nodes;
		}
	}

	public static class TreeNode {
		private final AbstractEntity entity;
		private String label;
		private final List<TreeNode> children = new ArrayList<TreeNode>();

		public TreeNode(String label, AbstractEntity e) {
			this.entity = e;
			this.label = label;
		}

		public TreeNode(AbstractEntity e) {
			entity = e;
			if (e instanceof BaseEntity) {
				label = ((BaseEntity) e).getBezeichnung();
			}
			if (e instanceof Person) {
				Person p = (Person) e;
				label = p.getVorname() + " " + p.getName();
			}
			if (e instanceof Jahr) {
				Jahr j = (Jahr) e;
				for (Lager l : j.getLager()) {
					children.add(new TreeNode(l));
				}
			}
			if (e instanceof Lager) {
				Lager l = (Lager) e;
				for (Gruppe g : l.getGruppe()) {
					children.add(new TreeNode(g));
				}
			}
			if (e instanceof Gruppe) {
				Gruppe g = (Gruppe) e;

				TreeNode leiterNode = new TreeNode("Leiter", g);
				children.add(leiterNode);

				TreeNode teilnehmerNode = new TreeNode("Teilnehmer", g);
				children.add(teilnehmerNode);

				for (Leiter l : g.getLeiter()) {
					leiterNode.getChildren().add(new TreeNode(l.getPerson()) {
						@Override
						public String getKey() {
							return "Leiter" + super.entity.toString();
						}
					});
				}
				for (Teilnehmer t : g.getTeilnehmer()) {
					teilnehmerNode.getChildren().add(new TreeNode(t.getPerson()) {
						@Override
						public String getKey() {
							return "Teilnehmer" + super.entity.toString();
						}
					});
				}
			}
		}

		public String getLabel() {
			return label;
		}

		public List<TreeNode> getChildren() {
			return children;
		}

		public AbstractEntity getParent() {
			return entity;
		}

		public String getKey() {
			return toString();
		}

		@Override
		public String toString() {
			return label + "[" + entity.toString() + "]";
		}
	}

}
