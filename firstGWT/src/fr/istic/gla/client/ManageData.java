package fr.istic.gla.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ManageData extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
}
