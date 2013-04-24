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
 * $Id: Address.java 5369 2010-02-24 14:58:19Z benoitf $
 * --------------------------------------------------------------------------
 */

package org.entities_ejb3;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Address of an employee.
 */
@Entity
@Access(AccessType.FIELD)
public class Address implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -581357686320492369L;

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Title for this address (home, extra, etc).
     */
    private String title = null;

    /**
     * City of this address.
     */
    private String city = null;

    /**
     * @return the title of the address.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Defines the title of this address.
     * @param title the given title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return the city of this address.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Defines the city of this address.
     * @param city the given city
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * @return string corresponding to this entity
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Address.class.getSimpleName());
        sb.append("[id=");
        sb.append(this.id);
        sb.append(", title=");
        sb.append(this.title);
        sb.append(", city=");
        sb.append(this.city);
        sb.append("]");

        return sb.toString();
    }
}
