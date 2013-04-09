/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.db.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "report",
    schema = "public"
)
public class Report extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private String name;
    private String generatorname;
    private Staff staff;
    private Date creationtime;
    private Date fromdate;
    private Date todate;
    private byte[] reportdocument;
    private Set<Activity> activities = new HashSet<Activity>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Report object.
     */
    public Report() {
    }

    /**
     * Creates a new Report object.
     *
     * @param  id              DOCUMENT ME!
     * @param  name            DOCUMENT ME!
     * @param  generatorname   DOCUMENT ME!
     * @param  creationtime    DOCUMENT ME!
     * @param  fromdate        DOCUMENT ME!
     * @param  todate          DOCUMENT ME!
     * @param  reportdocument  DOCUMENT ME!
     * @param  staff           DOCUMENT ME!
     */
    public Report(final long id,
            final String name,
            final String generatorname,
            final Date creationtime,
            final Date fromdate,
            final Date todate,
            final byte[] reportdocument,
            final Staff staff) {
        this.id = id;
        this.name = name;
        this.creationtime = creationtime;
        this.fromdate = fromdate;
        this.todate = todate;
        this.reportdocument = reportdocument;
        this.staff = staff;
        this.generatorname = generatorname;
    }

    /**
     * Creates a new Report object.
     *
     * @param  id              DOCUMENT ME!
     * @param  name            DOCUMENT ME!
     * @param  generatorname   DOCUMENT ME!
     * @param  creationtime    DOCUMENT ME!
     * @param  fromdate        DOCUMENT ME!
     * @param  todate          DOCUMENT ME!
     * @param  reportdocument  DOCUMENT ME!
     * @param  activities      DOCUMENT ME!
     * @param  staff           DOCUMENT ME!
     */
    public Report(final long id,
            final String name,
            final String generatorname,
            final Date creationtime,
            final Date fromdate,
            final Date todate,
            final byte[] reportdocument,
            final Set<Activity> activities,
            final Staff staff) {
        this.id = id;
        this.name = name;
        this.generatorname = generatorname;
        this.creationtime = creationtime;
        this.fromdate = fromdate;
        this.todate = todate;
        this.reportdocument = reportdocument;
        this.activities = activities;
        this.staff = staff;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "name",
        nullable = false,
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
    @Column(
        name = "generatorname",
        nullable = false,
        length = 50
    )
    public String getGeneratorname() {
        return this.generatorname;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  generatorname  DOCUMENT ME!
     */
    public void setGeneratorname(final String generatorname) {
        this.generatorname = generatorname;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(
        name = "reportdocument",
        nullable = false
    )
    public byte[] getReportdocument() {
        return this.reportdocument;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  reportdocument  DOCUMENT ME!
     */
    public void setReportdocument(final byte[] reportdocument) {
        this.reportdocument = reportdocument;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "creationtime",
        nullable = false,
        length = 29
    )
    public Date getCreationtime() {
        return this.creationtime;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  creationtime  DOCUMENT ME!
     */
    public void setCreationtime(final Date creationtime) {
        this.creationtime = creationtime;
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
        nullable = false,
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "staffid",
        nullable = true
    )
    public Staff getStaff() {
        return this.staff;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  staff  DOCUMENT ME!
     */
    public void setStaff(final Staff staff) {
        this.staff = staff;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "activity_report",
        schema = "public",
        joinColumns = {
                @JoinColumn(
                    name = "reportid",
                    nullable = false,
                    updatable = false
                )
            },
        inverseJoinColumns = {
                @JoinColumn(
                    name = "activityid",
                    nullable = false,
                    updatable = false
                )
            }
    )
    public Set<Activity> getActivities() {
        return this.activities;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  activities  DOCUMENT ME!
     */
    public void setActivities(final Set<Activity> activities) {
        this.activities = activities;
    }
}
