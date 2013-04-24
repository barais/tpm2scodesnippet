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
 * $Id: BusinessInterface.java 5369 2010-02-24 14:58:19Z benoitf $
 * --------------------------------------------------------------------------
 */

package fr.istic.ejb;

import java.util.List;

import javax.ejb.Remote;

import org.entities_ejb3.Employee;

/**
 * Business interface of the EJB 3.0.
 */
@Remote
public interface BusinessInterface {

    /**
     * Initialize the Database with new entities.
     */
    void init();

    /**
     * Find all employees.
     * @return list of employees
     */
    List<Employee> findEmployees();

    /**
     * Allows to find an employee by its name.
     * @param name the given name
     * @return the given employee, else null
     */
    Employee findEmployeeWithItsGivenName(final String name);

	String helloWorld();
}
