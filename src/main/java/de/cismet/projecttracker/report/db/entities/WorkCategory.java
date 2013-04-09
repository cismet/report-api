/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "work_category",
    schema = "public"
)
public class WorkCategory extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private String name;
    private boolean workpackagerelated = true;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WorkCategory object.
     */
    public WorkCategory() {
    }

    /**
     * Creates a new WorkCategory object.
     *
     * @param  id  DOCUMENT ME!
     */
    public WorkCategory(final long id) {
        this.id = id;
    }

    /**
     * Creates a new WorkCategory object.
     *
     * @param  id                  DOCUMENT ME!
     * @param  name                DOCUMENT ME!
     * @param  workpackagerelated  DOCUMENT ME!
     */
    public WorkCategory(final long id, final String name, final boolean workpackagerelated) {
        this.id = id;
        this.name = name;
        this.workpackagerelated = workpackagerelated;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "name",
        length = 50
    )
    public String getName() {
        return this.name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  name  DOCUMENT ME!
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(name = "workpackagerelated")
    public boolean getWorkpackagerelated() {
        return workpackagerelated;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workpackagerelated  DOCUMENT ME!
     */
    public void setWorkpackagerelated(final boolean workpackagerelated) {
        this.workpackagerelated = workpackagerelated;
    }
}
