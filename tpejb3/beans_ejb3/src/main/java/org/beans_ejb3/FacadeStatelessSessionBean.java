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
 * $Id: FacadeStatelessSessionBean.java 5369 2010-02-24 14:58:19Z benoitf $
 * --------------------------------------------------------------------------
 */

package org.beans_ejb3;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.entities_ejb3.Address;
import org.entities_ejb3.Employee;
import org.entities_ejb3.EmployeeLevelType;

import fr.istic.ejb.BusinessInterface;

/**
 * Stateless session bean that is using JPA 2.0.
 */
@Stateless(mappedName = "JPA2Bean")
public class FacadeStatelessSessionBean implements BusinessInterface {

    /**
     * Entity manager used by this session bean.
     */
    @PersistenceContext
    private EntityManager entityManager = null;

    /**
     * Initialize the Database with new entities.
     */
    public void init() {

        // Do nothing if already defined
        Employee test = findEmployeeWithItsGivenName("Florent");
        if (test != null) {
            return;
        }

        // The first employee
        Employee florent = new Employee();
        florent.setName("Florent");
        florent.addNickName("flo");
        florent.addNickName("FB");

        florent.getLevelRights().add(EmployeeLevelType.ADMIN);
        florent.getLevelRights().add(EmployeeLevelType.SUPER_ADMIN);

        // Create addresses
        Address florentWorkAddress = new Address();
        florentWorkAddress.setTitle("work");
        florentWorkAddress.setCity("Grenoble");
        this.entityManager.persist(florentWorkAddress);

        Address florentHomeAddress = new Address();
        florentHomeAddress.setTitle("home");
        florentHomeAddress.setCity("Grenoble");
        this.entityManager.persist(florentHomeAddress);

        florent.getAddresses().add(florentHomeAddress);
        florent.getAddresses().add(florentWorkAddress);

        Date now = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.set(2010, 11, 25);
        Date christmas = calendar.getTime();

        florent.getHistoryEvents().put(now, "Date of init");
        florent.getHistoryEvents().put(christmas, "XMas Day");

        this.entityManager.persist(florent);

        // Another employee
        Employee bean = new Employee();
        bean.setName("EasyBeans");
        bean.addNickName("The easy bean");
        bean.addNickName("OW2 EasyBeans");

        bean.getLevelRights().add(EmployeeLevelType.TRAINEE);

        // Create addresses
        Address beanFirstAddress = new Address();
        beanFirstAddress.setTitle("work");
        beanFirstAddress.setCity("Grenoble");

        Address beanSecondAddress = new Address();
        beanSecondAddress.setTitle("cofee");
        beanSecondAddress.setCity("Java");

        bean.getAddresses().add(beanFirstAddress);
        bean.getAddresses().add(beanSecondAddress);

        Calendar calendarRelease = new GregorianCalendar();
        calendarRelease.set(2010, 01, 01);
        Date release = calendarRelease.getTime();

        bean.getHistoryEvents().put(release, "Date of the release");

        this.entityManager.persist(bean);

    }

    /**
     * Find all employees.
     * @return list of employees
     */
    public List<Employee> findEmployees() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employee = criteriaQuery.from(Employee.class);
        return this.entityManager.createQuery(criteriaQuery.select(employee)).getResultList();
    }

    /**
     * Allows to find an employee by its name.
     * @param name the given name
     * @return the given employee, else null
     */
    public Employee findEmployeeWithItsGivenName(final String name) {

        // Use of JPA 2.0 criteria

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employee = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employee).where(criteriaBuilder.equal(employee.get("name"), name));
        try {
            return this.entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

    }

	public String helloWorld() {
		return "Hello le monde";
	}

}
