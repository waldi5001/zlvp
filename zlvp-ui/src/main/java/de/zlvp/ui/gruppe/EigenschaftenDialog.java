package de.zlvp.ui.gruppe;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import de.zlvp.control.GruppeController;
import de.zlvp.control.GruppeControllerAsync;
import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.AbstractEntity;
import de.zlvp.model.Gruppe;
import de.zlvp.ui.ProvideTreeContextMenuItems;
import de.zlvp.ui.bus.ReloadTreeEvent;
import de.zlvp.ui.bus.ZlvpEventBus;
import de.zlvp.ui.navigation.NavigationTree.TreeNode;

public class EigenschaftenDialog implements Editor<Gruppe>, ProvideTreeContextMenuItems {

	private static final EigenschaftenDialogUiBinder uiBinder = GWT.create(EigenschaftenDialogUiBinder.class);
	private static final GruppeDriver driver = GWT.create(GruppeDriver.class);

	private static final GruppeControllerAsync controller = GWT.create(GruppeController.class);

	interface EigenschaftenDialogUiBinder extends UiBinder<Widget, EigenschaftenDialog> {
	}

	interface GruppeDriver extends SimpleBeanEditorDriver<Gruppe, EigenschaftenDialog> {
	}

	@UiField
	Window window;

	@UiField
	TextField bezeichnung;

	@UiField
	TextArea schlachtruf;

	public void show() {
		uiBinder.createAndBindUi(this);
		driver.initialize(this);
		window.show();
	}

	public void edit(Gruppe person) {
		driver.edit(person);
	}

	@UiHandler("closeButton")
	public void onClose(SelectEvent event) {
		Gruppe gruppe = driver.flush();
		controller.speichern(EntityInfo.of(gruppe), gruppe.getBezeichnung(), gruppe.getSchlachtruf(),
				new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						ZlvpEventBus.get().fireEvent(new ReloadTreeEvent());
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});

		window.hide();
	}

	@Override
	public List<MenuItem> getMenuItems(final Object selectedItem) {
		MenuItem menuItem = new MenuItem("Eigenschaften anzeigen");
		menuItem.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				show();
				AbstractEntity parent = ((TreeNode) selectedItem).getParent();
				edit((Gruppe) parent);
			}
		});
		return Arrays.asList(menuItem);
	}

}
