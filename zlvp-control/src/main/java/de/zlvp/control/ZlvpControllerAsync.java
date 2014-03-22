package de.zlvp.control;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.zlvp.model.Jahr;

public interface ZlvpControllerAsync {
	void getJahre(AsyncCallback<List<Jahr>> callback);

	void getJahr(long id, AsyncCallback<Jahr> callback);
}
