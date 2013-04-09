/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.db.entities;

import java.util.Date;
import java.util.GregorianCalendar;

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
    name = "work_package_period",
    schema = "public",
    uniqueConstraints = @UniqueConstraint(columnNames = "asof")
)
public class WorkPackagePeriod extends BasicHibernateEntity implements Comparable<Object> {

    //~ Instance fields --------------------------------------------------------

    private WorkPackage workPackage;
    private Date fromdate;
    private Date todate;
    private Date asof;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WorkPackagePeriod object.
     */
    public WorkPackagePeriod() {
    }

    /**
     * Creates a new WorkPackagePeriod object.
     *
     * @param  id           DOCUMENT ME!
     * @param  workPackage  DOCUMENT ME!
     * @param  fromdate     DOCUMENT ME!
     * @param  asof         DOCUMENT ME!
     */
    public WorkPackagePeriod(final long id, final WorkPackage workPackage, final Date fromdate, final Date asof) {
        this.id = id;
        this.workPackage = workPackage;
        this.fromdate = fromdate;
        this.asof = asof;
    }

    /**
     * Creates a new WorkPackagePeriod object.
     *
     * @param  id           DOCUMENT ME!
     * @param  workPackage  DOCUMENT ME!
     * @param  fromdate     DOCUMENT ME!
     * @param  todate       DOCUMENT ME!
     * @param  asof         DOCUMENT ME!
     */
    public WorkPackagePeriod(final long id,
            final WorkPackage workPackage,
            final Date fromdate,
            final Date todate,
            final Date asof) {
        this.id = id;
        this.workPackage = workPackage;
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
        name = "workpackageid",
        nullable = false
    )
    public WorkPackage getWorkPackage() {
        return this.workPackage;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workPackage  DOCUMENT ME!
     */
    public void setWorkPackage(final WorkPackage workPackage) {
        this.workPackage = workPackage;
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

    @Override
    public int compareTo(final Object o) {
        if (o instanceof WorkPackagePeriod) {
            if ((o == null) || (((WorkPackagePeriod)o).asof == null)) {
                return -1;
            }
            if (asof == null) {
                return 1;
            }
            final long result = asof.getTime() - ((WorkPackagePeriod)o).asof.getTime();
            return (int)Math.signum(result);
        } else if (o instanceof Date) {
            final long result = asof.getTime() - ((Date)o).getTime();
            return (int)Math.signum(result);
        } else if (o instanceof GregorianCalendar) {
            final long result = asof.getTime() - ((GregorianCalendar)o).getTime().getTime();
            return (int)Math.signum(result);
        } else {
            return 0;
        }
    }
}
