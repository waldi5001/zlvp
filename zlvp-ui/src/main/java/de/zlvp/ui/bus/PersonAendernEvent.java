package de.zlvp.ui.bus;

import com.google.gwt.event.shared.GwtEvent;

import de.zlvp.model.Person;

public class PersonAendernEvent extends GwtEvent<PersonAendernEventHandler> {
	private static final GwtEvent.Type<PersonAendernEventHandler> TYPE = new GwtEvent.Type<PersonAendernEventHandler>();
	private final Person person;

	public static GwtEvent.Type<PersonAendernEventHandler> getType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PersonAendernEventHandler handler) {
		handler.personAendern(this);
	}

	@Override
	public GwtEvent.Type<PersonAendernEventHandler> getAssociatedType() {
		return getType();
	}

	public PersonAendernEvent(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

}