/**
 * EasyBeans
 * Copyright (C) 2010 Bull S.A.S.
 * Contact: easybeans@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * --------------------------------------------------------------------------
 * $Id: Client.java 5369 2010-02-24 14:58:19Z benoitf $
 * --------------------------------------------------------------------------
 */

package org.client_ejb3;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.entities_ejb3.Employee;

import fr.istic.ejb.BusinessInterface;

/**
 * Simple client of the JPA 2.0 example.
 */
public final class Client {

 
    /**
     * Utility class.
     */
    private Client() {

    }

    /**
     * Main method.
     * @param args the arguments (not required)
     * @throws Exception if exception is found.
     */
    public static void main(final String[] args) throws Exception {

        // Lookup the Remote Bean interface through JNDI
        Context initialContext = getInitialContext();
        BusinessInterface facadeBean = (BusinessInterface) initialContext.lookup("JPA2Bean");

        // Init (if not done)
        System.out.println("Initializing the database with some employees...");
        facadeBean.init();

        // Search Florent Employee
        System.out.println("Finding data about Florent...");
        Employee florent = facadeBean.findEmployeeWithItsGivenName("Florent");
        printEmployee(florent);

        System.out.println("Listing all employees...");
        for (Employee employee : facadeBean.findEmployees()) {
            printEmployee(employee);
        }

    }

    protected static void printEmployee(final Employee employee) {
        String name = employee.getName();

        // Display nickNames
        System.out.println("NickNames of '" + name + "' are : " + employee.getNickNames());

        // Level rights
        System.out.println("Level Rights of '" + name + "' are : " + employee.getLevelRights());

        // Addresses
        System.out.println("Addresses (in the insert order) of '" + name + "' are : " + employee.getAddresses());


        // Addresses
        System.out.println("History events of '" + name + "' are : " + employee.getHistoryEvents());

    }

    /**
     * Use Smart Factory by default.
     */
    private static final String DEFAULT_INITIAL_CONTEXT_FACTORY = "org.ow2.easybeans.component.smartclient.spi.SmartContextFactory";

    /**
     * @return Returns the InitialContext.
     * @throws NamingException If the Context cannot be created.
     */
    private static Context getInitialContext() throws NamingException {

        // if user don't use jclient/client container
        // we can specify the InitialContextFactory to use
        // But this is *not recommended*.
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, getInitialContextFactory());

        // Usually a simple new InitialContext() without any parameters is
        // sufficent.
        // return new InitialContext();

        return new InitialContext(env);
    }

    /**
     * Returns a configurable InitialContextFactory classname.<br/>
     * Can be configured with the
     * <code>easybeans.client.initial-context-factory</code> System property.
     * @return Returns a configurable InitialContextFactory classname.
     */
    private static String getInitialContextFactory() {
        String prop = System.getProperty("easybeans.client.initial-context-factory");
        // If not found, use the default
        if (prop == null) {
            prop = DEFAULT_INITIAL_CONTEXT_FACTORY;
        }
        return prop;
    }

}
