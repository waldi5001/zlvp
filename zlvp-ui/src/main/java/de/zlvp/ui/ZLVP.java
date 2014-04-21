package de.zlvp.ui;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ContentPanel.ContentPanelAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;

import de.zlvp.control.PersonController;
import de.zlvp.control.PersonControllerAsync;
import de.zlvp.model.Person;
import de.zlvp.ui.navigation.NavigationTree;
import de.zlvp.ui.navigation.NavigationTree.TreeNode;
import de.zlvp.ui.navigation.NavigationTreeSelectionModel;
import de.zlvp.ui.person.PersonGrid;

public class ZLVP implements EntryPoint, IsWidget {

	@UiField
	SimpleContainer centerContainer;

	@UiField
	AccordionLayoutContainer eastContainer;

	@UiField
	PersonGrid personGrid;

	@UiField
	NavigationTree tree;

	protected interface ZLVPUiBinder extends UiBinder<Widget, ZLVP> {
	}

	private static ZLVPUiBinder uiBinder = GWT.create(ZLVPUiBinder.class);

	public PersonControllerAsync controller = GWT.create(PersonController.class);

	@Override
	public void onModuleLoad() {
		controller.getPersons(new AsyncCallback<List<Person>>() {

			@Override
			public void onSuccess(List<Person> result) {
				personGrid.setPerson(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught);
			}
		});

		Viewport viewport = new Viewport();
		viewport.setWidget(asWidget());
		RootPanel.get().add(viewport);
	}

	@Override
	public Widget asWidget() {
		Widget widget = uiBinder.createAndBindUi(this);
		tree.setSelectionModel(new NavigationTreeSelectionModel<TreeNode>(centerContainer));
		eastContainer.setActiveWidget(eastContainer.getWidget(0));
		return widget;
	}

	@UiFactory
	public ContentPanel createContentPanel(ContentPanelAppearance appearance) {
		return new ContentPanel(appearance);
	}

}
