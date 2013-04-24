package fr.istic.gla.server;

import javax.persistence.EntityManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.istic.gla.client.ManageData;
import fr.istic.gla.shared.A;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ManageDataImpl extends RemoteServiceServlet implements
		ManageData {

	public String greetServer(String input) throws IllegalArgumentException {

		

	    EntityManager em = EMF.get().createEntityManager();
	    em.getTransaction().begin();
	    
	    A a = new A();
	    em.persist(a);
	    em.getTransaction().commit();

		
		// Verify that the input is valid. 
		return input.toUpperCase();
				}


}
