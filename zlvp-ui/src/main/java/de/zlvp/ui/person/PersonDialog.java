package de.zlvp.ui.person;

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
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import de.zlvp.control.PersonControllerAsync;
import de.zlvp.control.dto.EntityInfo;
import de.zlvp.model.Person;
import de.zlvp.ui.ControllerFactory;
import de.zlvp.ui.DefaultAsyncCallback;
import de.zlvp.ui.ProvideTreeContextMenuItems;
import de.zlvp.ui.bus.ReloadTreeEvent;
import de.zlvp.ui.bus.ZlvpEventBus;
import de.zlvp.ui.navigation.NavigationTree.TreeNode;

public class PersonDialog implements Editor<Person>, ProvideTreeContextMenuItems {

	private static final EigenschaftenDialogUiBinder uiBinder = GWT.create(EigenschaftenDialogUiBinder.class);
	private static final PersonDriver driver = GWT.create(PersonDriver.class);

	private static final PersonControllerAsync controller = ControllerFactory.getPersonController();

	interface EigenschaftenDialogUiBinder extends UiBinder<Widget, PersonDialog> {
	}

	interface PersonDriver extends SimpleBeanEditorDriver<Person, PersonDialog> {
	}

	@UiField
	Window window;

	@UiField
	TextField name;

	@UiField
	TextField vorname;

	@UiField
	TextField strasse;

	@UiField
	TextField plz;

	@UiField
	TextField ort;

	public void show() {
		uiBinder.createAndBindUi(this);
		driver.initialize(this);
		window.show();
	}

	public void edit(Person person) {
		driver.edit(person);
	}

	@UiHandler("closeButton")
	public void onClose(SelectEvent event) {
		Person person = driver.flush();
		controller.save(EntityInfo.of(person), person.getName(), person.getVorname(), person.getStrasse(),
				person.getPlz(), person.getOrt(), person.getGeburtsdatum(), new DefaultAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						ZlvpEventBus.get().fireEvent(new ReloadTreeEvent());
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
				edit((Person) ((TreeNode) selectedItem).getParent());
			}
		});
		return Arrays.asList(menuItem);
	}

}
