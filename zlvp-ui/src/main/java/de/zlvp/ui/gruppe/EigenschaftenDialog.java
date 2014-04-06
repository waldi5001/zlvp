package de.zlvp.ui.gruppe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

import de.zlvp.model.Gruppe;

public class EigenschaftenDialog implements IsWidget, Editor<Gruppe> {

	private static final EigenschaftenDialogUiBinder uiBinder = GWT.create(EigenschaftenDialogUiBinder.class);
	private static final GruppeDriver driver = GWT.create(GruppeDriver.class);

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

	@Override
	public Widget asWidget() {
		Widget widget = uiBinder.createAndBindUi(this);

		driver.initialize(this);

		window.show();
		return widget;
	}

	public void edit(Gruppe person) {
		driver.edit(person);
	}

	@UiHandler("closeButton")
	public void onClose(SelectEvent event) {
		Gruppe person = driver.flush();
		System.out.println(person);
		window.hide();
	}

}
