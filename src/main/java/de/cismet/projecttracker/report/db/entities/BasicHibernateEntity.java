/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.projecttracker.report.db.entities;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public abstract class BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    protected long id;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Id
    @Column(
        name = "id",
        unique = true,
        nullable = false
    )
    public long getId() {
        return this.id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    public void setId(final long id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object obj) {
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
