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
 * $Id: Employee.java 5369 2010-02-24 14:58:19Z benoitf $
 * --------------------------------------------------------------------------
 */

package org.entities_ejb3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyTemporal;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.TemporalType;

/**
 * A JPA 2.0 Entity.
 */
@Entity
public class Employee implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -7448829558275268188L;

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * The name of the employee.
     */
    private String name;

    /**
     * List of nicknames of the employee.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> nickNames = null;

    /**
     * User Rights.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<EmployeeLevelType> levelRights = null;

    /**
     * Example of a Map between a date and an event that occurs on this
     * employee.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "historyEvents")
    @MapKeyColumn(name = "dateEvent")
    @Column(name = "event")
    @MapKeyTemporal(TemporalType.DATE)
    private Map<Date, String> historyEvents;

    /**
     * Keep ordered elements.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "index")
    private List<Address> addresses = null;

    /**
     * Default constructor.
     */
    public Employee() {
        this.nickNames = new ArrayList<String>();
        this.addresses = new ArrayList<Address>();
        this.historyEvents = new HashMap<Date, String>();
        this.levelRights = new HashSet<EmployeeLevelType>();
    }

    /**
     * Defines name of the employee.
     * @param name the given name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return name of the employee
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return id of the
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return list of nicknames of this employee.
     */
    public List<String> getNickNames() {
        return this.nickNames;
    }

    /**
     * Adds the given nickname.
     * @param nickName the given nickname
     */
    public void addNickName(final String nickName) {
        this.nickNames.add(nickName);
    }

    /**
     * @return level rights
     */
    public Set<EmployeeLevelType> getLevelRights() {
        return this.levelRights;
    }

    /**
     * @return adresses of this employee
     */
    public List<Address> getAddresses() {
        return this.addresses;
    }

    /**
     * @return history events of this employee
     */
    public Map<Date, String> getHistoryEvents() {
        return this.historyEvents;
    }

    /**
     * @return string corresponding to this entity
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Employee.class.getSimpleName());
        sb.append("[id=");
        sb.append(this.id);
        sb.append(", name=");
        sb.append(this.name);

        sb.append("]");

        return sb.toString();
    }
}
