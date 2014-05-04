package de.zlvp.ui;

public abstract class DefaultAsyncCallback<T> implements com.google.gwt.user.client.rpc.AsyncCallback<T> {

	@Override
	public void onFailure(Throwable caught) {
		System.out.println(caught);
	}

	@Override
	public abstract void onSuccess(T result);

}
