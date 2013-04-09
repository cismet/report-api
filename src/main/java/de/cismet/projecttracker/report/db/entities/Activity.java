/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.db.entities;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.cismet.projecttracker.report.helper.CalendarHelper;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "activity",
    schema = "public"
)
public class Activity extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private Staff staff;
    private WorkPackage workPackage;
    private WorkCategory workCategory;
    private double workinghours;
    private boolean committed = false;
    private String description;
    private Date day;
    private int kindofactivity;
    private Set<Report> reports = new HashSet<Report>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Activity object.
     */
    public Activity() {
    }

    /**
     * Creates a new Activity object.
     *
     * @param  id              DOCUMENT ME!
     * @param  day             DOCUMENT ME!
     * @param  staff           DOCUMENT ME!
     * @param  workPackage     DOCUMENT ME!
     * @param  workinghours    DOCUMENT ME!
     * @param  description     DOCUMENT ME!
     * @param  committed       DOCUMENT ME!
     * @param  kindofactivity  DOCUMENT ME!
     */
    public Activity(final long id,
            final Date day,
            final Staff staff,
            final WorkPackage workPackage,
            final double workinghours,
            final String description,
            final boolean committed,
            final int kindofactivity) {
        this.id = id;
        this.day = day;
        this.staff = staff;
        this.workPackage = workPackage;
        this.workinghours = workinghours;
        this.description = description;
        this.committed = committed;
        this.kindofactivity = kindofactivity;
    }

    /**
     * Creates a new Activity object.
     *
     * @param  id              DOCUMENT ME!
     * @param  day             DOCUMENT ME!
     * @param  staff           DOCUMENT ME!
     * @param  workPackage     DOCUMENT ME!
     * @param  workinghours    DOCUMENT ME!
     * @param  description     DOCUMENT ME!
     * @param  committed       DOCUMENT ME!
     * @param  kindofactivity  DOCUMENT ME!
     * @param  workCategory    DOCUMENT ME!
     * @param  reports         DOCUMENT ME!
     */
    public Activity(final long id,
            final Date day,
            final Staff staff,
            final WorkPackage workPackage,
            final double workinghours,
            final String description,
            final boolean committed,
            final int kindofactivity,
            final WorkCategory workCategory,
            final Set<Report> reports) {
        this.id = id;
        this.day = day;
        this.staff = staff;
        this.workPackage = workPackage;
        this.committed = committed;
        this.workinghours = workinghours;
        this.description = description;
        this.workCategory = workCategory;
        this.reports = reports;
        this.kindofactivity = kindofactivity;
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
        nullable = true
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
    @Column(
        name = "workinghours",
        nullable = false,
        precision = 6
    )
    public double getWorkinghours() {
        return this.workinghours;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workinghours  DOCUMENT ME!
     */
    public void setWorkinghours(final double workinghours) {
        this.workinghours = workinghours;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "description",
        nullable = true
    )
    public String getDescription() {
        return this.description;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  description  DOCUMENT ME!
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(name = "committed")
    public boolean getCommitted() {
        return committed;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  committed  DOCUMENT ME!
     */
    public void setCommitted(final boolean committed) {
        this.committed = committed;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "kindofactivity",
        nullable = false
    )
    public int getKindofactivity() {
        return this.kindofactivity;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kindofactivity  DOCUMENT ME!
     */
    public void setKindofactivity(final int kindofactivity) {
        this.kindofactivity = kindofactivity;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "day",
        nullable = true,
        length = 29
    )
    public Date getDay() {
        return this.day;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  day  DOCUMENT ME!
     */
    public void setDay(final Date day) {
        this.day = day;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "staffid",
        nullable = false
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "categoryid",
        nullable = true
    )
    public WorkCategory getWorkCategory() {
        return this.workCategory;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workCategory  DOCUMENT ME!
     */
    public void setWorkCategory(final WorkCategory workCategory) {
        this.workCategory = workCategory;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "activity_report",
        schema = "public",
        joinColumns = {
                @JoinColumn(
                    name = "activityid",
                    nullable = false,
                    updatable = false
                )
            },
        inverseJoinColumns = {
                @JoinColumn(
                    name = "reportid",
                    nullable = false,
                    updatable = false
                )
            }
    )
    public Set<Report> getReports() {
        return this.reports;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  reports  DOCUMENT ME!
     */
    public void setReports(final Set<Report> reports) {
        this.reports = reports;
    }

    @Override
    public String toString() {
        if (kindofactivity == 0) {
            return CalendarHelper.toDateString(day) + "\n" + ((workPackage != null) ? workPackage.getName() : "") + "\n"
                        + description + "\n" + workinghours;
        } else {
            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            final Date dayDate = new Date(day.getTime());
            // TODO lock day action
            String action;
            if (kindofactivity == 1) {
                action = "COME";
            } else if (kindofactivity == 3) {
                action = "LOCK";
            } else {
                action = "GO";
            }
            return action + " at " + formatter.format(dayDate);
        }
    }
}
