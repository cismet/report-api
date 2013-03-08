/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.db.entities;

import javax.persistence.Id;
import javax.persistence.Column;

/**
 *
 * @author therter
 */
public abstract class BasicHibernateEntity {
    protected long id;

    @Id
    @Column(name="id", unique=true, nullable=false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BasicHibernateEntity) {
            if (obj.getClass().getName().equals(this.getClass().getName())) {
                return this.getId() == ((BasicHibernateEntity)obj).getId();
            }
        }

        return false;
    }

    
    @Override
    public int hashCode() {
        return (int)(id % Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
