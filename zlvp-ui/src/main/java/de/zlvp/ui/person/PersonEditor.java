package de.zlvp.ui.person;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;

import de.zlvp.control.PersonController;
import de.zlvp.control.PersonControllerAsync;
import de.zlvp.model.Person;
import de.zlvp.ui.bus.ReloadTreeEvent;
import de.zlvp.ui.bus.ZlvpEventBus;

public class PersonEditor extends Composite implements Editor<Person> {

	interface PersonDriver extends SimpleBeanEditorDriver<Person, PersonEditor> {
	}

	interface PersonEditorUiBinder extends UiBinder<Widget, PersonEditor> {
	}

	public PersonControllerAsync controller = GWT.create(PersonController.class);
	private static PersonEditorUiBinder uiBinder = GWT.create(PersonEditorUiBinder.class);
	private static PersonDriver driver = GWT.create(PersonDriver.class);

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

	@UiField
	DateField geburtsdatum;

	public PersonEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
	}

	public void editPerson(Person person) {
		driver.edit(person);
	}

	@UiHandler("save")
	public void onSaveSelect(SelectEvent event) {
		Person person = driver.flush();
		controller.savePerson(person, new AsyncCallback<Person>() {
			@Override
			public void onSuccess(Person result) {
				ZlvpEventBus.get().fireEvent(new ReloadTreeEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure");
			}
		});
	}

	@UiHandler("cancel")
	public void onCancelSelect(SelectEvent event) {
		System.out.println("Cancel");
	}

}
