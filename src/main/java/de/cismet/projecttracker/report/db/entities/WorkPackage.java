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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.cismet.projecttracker.report.commons.TimePeriod;
import de.cismet.projecttracker.report.helper.QueryHelper;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "work_package",
    schema = "public"
)
public class WorkPackage extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private WorkPackage workPackage;
    private Project project;
    private CostCategory costCategory;
    private Staff responsiblestaff;
    private String name;
    private String description;
    private String expirationDescription;
    private String abbreviation;
    private double warnlevel;
    private double criticallevel;
    private double fullstoplevel;
    private boolean issubversion = false;
    private Set<WorkPackage> workPackages = new HashSet<WorkPackage>(0);
    private Set<WorkPackagePeriod> workPackagePeriods = new HashSet<WorkPackagePeriod>(0);
    private Set<Activity> activityWorkPackages = new HashSet<Activity>(0);
    private Set<ProjectComponentTag> projectComponentTags = new HashSet<ProjectComponentTag>(0);
    private Set<EstimatedComponentCost> estimatedWorkPackageCosts = new HashSet<EstimatedComponentCost>(0);
    private Set<WorkPackageProgress> workPackageProgresses = new HashSet<WorkPackageProgress>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WorkPackage object.
     */
    public WorkPackage() {
    }

    /**
     * Creates a new WorkPackage object.
     *
     * @param  id                DOCUMENT ME!
     * @param  project           DOCUMENT ME!
     * @param  responsiblestaff  DOCUMENT ME!
     * @param  abbreviation      DOCUMENT ME!
     * @param  name              DOCUMENT ME!
     */
    public WorkPackage(final long id,
            final Project project,
            final Staff responsiblestaff,
            final String abbreviation,
            final String name) {
        this.id = id;
        this.responsiblestaff = responsiblestaff;
        this.project = project;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    /**
     * Creates a new WorkPackage object.
     *
     * @param  id                         DOCUMENT ME!
     * @param  responsiblestaff           DOCUMENT ME!
     * @param  workPackage                DOCUMENT ME!
     * @param  project                    DOCUMENT ME!
     * @param  name                       DOCUMENT ME!
     * @param  issubversion               DOCUMENT ME!
     * @param  warnlevel                  DOCUMENT ME!
     * @param  criticallevel              DOCUMENT ME!
     * @param  fullstoplevel              DOCUMENT ME!
     * @param  abbreviation               DOCUMENT ME!
     * @param  description                DOCUMENT ME!
     * @param  workPackages               DOCUMENT ME!
     * @param  workPackagePeriods         DOCUMENT ME!
     * @param  activityWorkPackages       DOCUMENT ME!
     * @param  projectComponentTags       DOCUMENT ME!
     * @param  estimatedWorkPackageCosts  DOCUMENT ME!
     * @param  costCategory               DOCUMENT ME!
     * @param  workPackageProgresses      DOCUMENT ME!
     */
    public WorkPackage(final long id,
            final Staff responsiblestaff,
            final WorkPackage workPackage,
            final Project project,
            final String name,
            final boolean issubversion,
            final double warnlevel,
            final double criticallevel,
            final double fullstoplevel,
            final String abbreviation,
            final String description,
            final Set<WorkPackage> workPackages,
            final Set<WorkPackagePeriod> workPackagePeriods,
            final Set<Activity> activityWorkPackages,
            final Set<ProjectComponentTag> projectComponentTags,
            final Set<EstimatedComponentCost> estimatedWorkPackageCosts,
            final CostCategory costCategory,
            final Set<WorkPackageProgress> workPackageProgresses) {
        this.id = id;
        this.responsiblestaff = responsiblestaff;
        this.workPackage = workPackage;
        this.project = project;
        this.name = name;
        this.description = description;
        this.workPackages = workPackages;
        this.workPackagePeriods = workPackagePeriods;
        this.activityWorkPackages = activityWorkPackages;
        this.projectComponentTags = projectComponentTags;
        this.estimatedWorkPackageCosts = estimatedWorkPackageCosts;
        this.costCategory = costCategory;
        this.abbreviation = abbreviation;
        this.issubversion = issubversion;
        this.warnlevel = warnlevel;
        this.criticallevel = criticallevel;
        this.fullstoplevel = fullstoplevel;
        this.workPackageProgresses = workPackageProgresses;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentworkpackage")
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "project",
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "responsiblestaff",
        nullable = true
    )
    public Staff getResponsiblestaff() {
        return this.responsiblestaff;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  responsiblestaff  DOCUMENT ME!
     */
    public void setResponsiblestaff(final Staff responsiblestaff) {
        this.responsiblestaff = responsiblestaff;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "costcategoryid",
        nullable = false
    )
    public CostCategory getCostCategory() {
        return this.costCategory;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  costCategory  DOCUMENT ME!
     */
    public void setCostCategory(final CostCategory costCategory) {
        this.costCategory = costCategory;
    }

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
        name = "abbreviation",
        nullable = false,
        length = 50
    )
    public String getAbbreviation() {
        return this.abbreviation;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  abbreviation  DOCUMENT ME!
     */
    public void setAbbreviation(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(name = "description")
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
    public String getExpirationDescription() {
        return expirationDescription;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  expirationDescription  DOCUMENT ME!
     */
    public void setExpirationDescription(final String expirationDescription) {
        this.expirationDescription = expirationDescription;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "workPackage"
    )
    public Set<WorkPackage> getWorkPackages() {
        return this.workPackages;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workPackages  DOCUMENT ME!
     */
    public void setWorkPackages(final Set<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "workPackage"
    )
    public Set<WorkPackagePeriod> getWorkPackagePeriods() {
        return this.workPackagePeriods;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workPackagePeriods  DOCUMENT ME!
     */
    public void setWorkPackagePeriods(final Set<WorkPackagePeriod> workPackagePeriods) {
        this.workPackagePeriods = workPackagePeriods;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "workPackage"
    )
    public Set<Activity> getActivityWorkPackages() {
        return this.activityWorkPackages;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  activityWorkPackages  DOCUMENT ME!
     */
    public void setActivityWorkPackages(final Set<Activity> activityWorkPackages) {
        this.activityWorkPackages = activityWorkPackages;
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
        name = "project_component_tag_project_component",
        schema = "public",
        joinColumns = {
                @JoinColumn(
                    name = "projectcomponentid",
                    nullable = false,
                    updatable = false
                )
            },
        inverseJoinColumns = {
                @JoinColumn(
                    name = "projectcomponenttagid",
                    nullable = false,
                    updatable = false
                )
            }
    )
    public Set<ProjectComponentTag> getProjectComponentTags() {
        return this.projectComponentTags;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projectComponentTags  DOCUMENT ME!
     */
    public void setProjectComponentTags(final Set<ProjectComponentTag> projectComponentTags) {
        this.projectComponentTags = projectComponentTags;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "warnlevel",
        nullable = true,
        precision = 10
    )
    public double getWarnlevel() {
        return this.warnlevel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  warnlevel  DOCUMENT ME!
     */
    public void setWarnlevel(final double warnlevel) {
        this.warnlevel = warnlevel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "criticallevel",
        nullable = true,
        precision = 10
    )
    public double getCriticallevel() {
        return this.criticallevel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  criticallevel  DOCUMENT ME!
     */
    public void setCriticallevel(final double criticallevel) {
        this.criticallevel = criticallevel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "fullstoplevel",
        nullable = true,
        precision = 10
    )
    public double getFullstoplevel() {
        return this.fullstoplevel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fullstoplevel  DOCUMENT ME!
     */
    public void setFullstoplevel(final double fullstoplevel) {
        this.fullstoplevel = fullstoplevel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "workPackage"
    )
    public Set<EstimatedComponentCost> getEstimatedWorkPackageCosts() {
        return this.estimatedWorkPackageCosts;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  estimatedWorkPackageCosts  DOCUMENT ME!
     */
    public void setEstimatedWorkPackageCosts(final Set<EstimatedComponentCost> estimatedWorkPackageCosts) {
        this.estimatedWorkPackageCosts = estimatedWorkPackageCosts;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(name = "issubversion")
    public boolean getIssubversion() {
        return this.issubversion;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  issubversion  DOCUMENT ME!
     */
    public void setIssubversion(final boolean issubversion) {
        this.issubversion = issubversion;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "workPackage"
    )
    public Set<WorkPackageProgress> getWorkPackageProgresses() {
        return this.workPackageProgresses;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workPackageProgresses  DOCUMENT ME!
     */
    public void setWorkPackageProgresses(final Set<WorkPackageProgress> workPackageProgresses) {
        this.workPackageProgresses = workPackageProgresses;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof WorkPackage) {
            final WorkPackage other = (WorkPackage)obj;
            // the variable other must access the id variable with the get method, because this object is
            // a hibernate proxy object
            if (other.getId() == id) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = (59 * hash) + (int)(this.id ^ (this.id >>> 32));
        return hash;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   date  cal
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  if the given date is not within the most recent project period
     */
    public int getWorkPackageQuarter(final Date date) throws IllegalArgumentException {
        final int projectQuarter = ((getWorkpackageMonth(date) - 1) / 3);

        return projectQuarter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   date  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    public int getWorkpackageMonth(final Date date) throws IllegalArgumentException {
        final WorkPackagePeriod period = QueryHelper.getMostRecentPeriod(this);
        final Date start = period.getFromdate();
        final int year = date.getYear() - start.getYear();
        final int months = date.getMonth() - start.getMonth();
        int day = 1;

        if ((date.getTime() > period.getTodate().getTime()) || (date.getTime() < period.getFromdate().getTime())) {
            throw new IllegalArgumentException();
        }

        if (date.getDate() < start.getDate()) {
            day = 0;
        }

        return months + (year * 12) + day;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   month  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    public TimePeriod getTimeperiodOfMonth(final int month) throws IllegalArgumentException {
        final TimePeriod period = new TimePeriod();
        final WorkPackagePeriod mostRecentPeriod = QueryHelper.getMostRecentPeriod(this);
        final GregorianCalendar newBegin = new GregorianCalendar();
        final GregorianCalendar end = new GregorianCalendar();

        newBegin.setTime(mostRecentPeriod.getFromdate());
        end.setTime(mostRecentPeriod.getTodate());
        newBegin.add(GregorianCalendar.MONTH, month - 1);

        if (newBegin.after(end)) {
            throw new IllegalArgumentException();
        }

        final GregorianCalendar newEnd = (GregorianCalendar)newBegin.clone();
        newEnd.add(GregorianCalendar.MONTH, 1);
        newEnd.add(GregorianCalendar.DATE, -1);
        period.setStart(newBegin);

        if (end.after(end)) {
            period.setEnd(end);
        } else {
            period.setEnd(newEnd);
        }

        return period;
    }
}
