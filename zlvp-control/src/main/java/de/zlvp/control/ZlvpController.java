package de.zlvp.control;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.zlvp.model.Jahr;

@RemoteServiceRelativePath("rpc/ZlvpController")
public interface ZlvpController extends RemoteService {

	List<Jahr> getJahre();

	Jahr getJahr(long id);

}
