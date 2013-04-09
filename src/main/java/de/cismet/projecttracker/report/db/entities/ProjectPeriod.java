/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.db.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "project_period",
    schema = "public",
    uniqueConstraints = @UniqueConstraint(columnNames = "asof")
)
public class ProjectPeriod extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private Project project;
    private Date fromdate;
    private Date todate;
    private Date asof;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ProjectPeriod object.
     */
    public ProjectPeriod() {
    }

    /**
     * Creates a new ProjectPeriod object.
     *
     * @param  id        DOCUMENT ME!
     * @param  project   DOCUMENT ME!
     * @param  fromdate  DOCUMENT ME!
     * @param  asof      DOCUMENT ME!
     */
    public ProjectPeriod(final long id, final Project project, final Date fromdate, final Date asof) {
        this.id = id;
        this.project = project;
        this.fromdate = fromdate;
        this.asof = asof;
    }

    /**
     * Creates a new ProjectPeriod object.
     *
     * @param  id        DOCUMENT ME!
     * @param  project   DOCUMENT ME!
     * @param  fromdate  DOCUMENT ME!
     * @param  todate    DOCUMENT ME!
     * @param  asof      DOCUMENT ME!
     */
    public ProjectPeriod(final long id,
            final Project project,
            final Date fromdate,
            final Date todate,
            final Date asof) {
        this.id = id;
        this.project = project;
        this.fromdate = fromdate;
        this.todate = todate;
        this.asof = asof;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "projectid",
        nullable = false
    )
    public Project getProject() {
        return this.project;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  project  DOCUMENT ME!
     */
    public void setProject(final Project project) {
        this.project = project;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "fromdate",
        nullable = false,
        length = 29
    )
    public Date getFromdate() {
        return this.fromdate;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fromdate  DOCUMENT ME!
     */
    public void setFromdate(final Date fromdate) {
        this.fromdate = fromdate;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "todate",
        length = 29
    )
    public Date getTodate() {
        return this.todate;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  todate  DOCUMENT ME!
     */
    public void setTodate(final Date todate) {
        this.todate = todate;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "asof",
        unique = true,
        nullable = false,
        length = 29
    )
    public Date getAsof() {
        return this.asof;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  asof  DOCUMENT ME!
     */
    public void setAsof(final Date asof) {
        this.asof = asof;
    }
}
