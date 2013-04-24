package fr.istic.gla.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ManageDataAsync {

	void greetServer(String name, AsyncCallback<String> callback);
	//void greetServer(String input, AsyncCallback<String> callback)
	//		throws IllegalArgumentException;
}
