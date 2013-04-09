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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import de.cismet.projecttracker.report.commons.TimePeriod;
import de.cismet.projecttracker.report.helper.QueryHelper;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "project",
    schema = "public",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Project extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private ProjectBody projectBody;
    private ProjectCategory projectCategory;
    private Project project;
    private Staff responsiblestaff;
    private String name;
    private String description;
    private double overheadratio;
    private double hoursofamanday;
    private double daysofamanmonth;
    private double warnlevel;
    private double criticallevel;
    private double fullstoplevel;
    private boolean overtimehoursallowed = true;
    private Set<Project> projects = new HashSet<Project>(0);
    private Set<ProjectPeriod> projectPeriods = new HashSet<ProjectPeriod>(0);
    private Set<CostCategory> costCategories = new HashSet<CostCategory>(0);
    private Set<WorkPackage> workPackages = new HashSet<WorkPackage>(0);
    private Set<ProjectComponentTag> projectComponentTags = new HashSet<ProjectComponentTag>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Project object.
     */
    public Project() {
    }

    /**
     * Creates a new Project object.
     *
     * @param  id    DOCUMENT ME!
     * @param  name  DOCUMENT ME!
     */
    public Project(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Creates a new Project object.
     *
     * @param  id                    DOCUMENT ME!
     * @param  responsiblestaff      DOCUMENT ME!
     * @param  projectBody           DOCUMENT ME!
     * @param  project               DOCUMENT ME!
     * @param  name                  DOCUMENT ME!
     * @param  hoursofamanday        DOCUMENT ME!
     * @param  daysofamanmonth       DOCUMENT ME!
     * @param  overtimehoursallowed  DOCUMENT ME!
     * @param  description           DOCUMENT ME!
     * @param  overheadratio         DOCUMENT ME!
     * @param  warnlevel             DOCUMENT ME!
     * @param  criticallevel         DOCUMENT ME!
     * @param  fullstoplevel         DOCUMENT ME!
     * @param  projects              DOCUMENT ME!
     * @param  projectPeriods        DOCUMENT ME!
     * @param  costCategories        DOCUMENT ME!
     * @param  workPackages          DOCUMENT ME!
     * @param  projectComponentTags  DOCUMENT ME!
     * @param  projectCategory       DOCUMENT ME!
     */
    public Project(final long id,
            final Staff responsiblestaff,
            final ProjectBody projectBody,
            final Project project,
            final String name,
            final double hoursofamanday,
            final double daysofamanmonth,
            final boolean overtimehoursallowed,
            final String description,
            final double overheadratio,
            final double warnlevel,
            final double criticallevel,
            final double fullstoplevel,
            final Set<Project> projects,
            final Set<ProjectPeriod> projectPeriods,
            final Set<CostCategory> costCategories,
            final Set<WorkPackage> workPackages,
            final Set<ProjectComponentTag> projectComponentTags,
            final ProjectCategory projectCategory) {
        this.id = id;
        this.responsiblestaff = responsiblestaff;
        this.projectBody = projectBody;
        this.project = project;
        this.name = name;
        this.description = description;
        this.overheadratio = overheadratio;
        this.projects = projects;
        this.projectPeriods = projectPeriods;
        this.costCategories = costCategories;
        this.workPackages = workPackages;
        this.projectCategory = projectCategory;
        this.projectComponentTags = projectComponentTags;
        this.hoursofamanday = hoursofamanday;
        this.daysofamanmonth = daysofamanmonth;
        this.overtimehoursallowed = overtimehoursallowed;
        this.warnlevel = warnlevel;
        this.criticallevel = criticallevel;
        this.fullstoplevel = fullstoplevel;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectbodyid")
    public ProjectBody getProjectBody() {
        return this.projectBody;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projectBody  DOCUMENT ME!
     */
    public void setProjectBody(final ProjectBody projectBody) {
        this.projectBody = projectBody;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryid")
    public ProjectCategory getProjectCategory() {
        return this.projectCategory;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projectCategory  DOCUMENT ME!
     */
    public void setProjectCategory(final ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentproject")
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
    @Column(
        name = "name",
        unique = true,
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
    @Column(
        name = "overheadratio",
        precision = 6
    )
    public double getOverheadratio() {
        return this.overheadratio;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  overheadratio  DOCUMENT ME!
     */
    public void setOverheadratio(final double overheadratio) {
        this.overheadratio = overheadratio;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "hoursofamanday",
        precision = 6
    )
    public double getHoursofamanday() {
        return this.hoursofamanday;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hoursofamanday  DOCUMENT ME!
     */
    public void setHoursofamanday(final double hoursofamanday) {
        this.hoursofamanday = hoursofamanday;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "daysofamanmonth",
        precision = 6
    )
    public double getDaysofamanmonth() {
        return this.daysofamanmonth;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  daysofamanmonth  DOCUMENT ME!
     */
    public void setDaysofamanmonth(final double daysofamanmonth) {
        this.daysofamanmonth = daysofamanmonth;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "overtimehoursallowed",
        precision = 6
    )
    public boolean getOvertimehoursallowed() {
        return this.overtimehoursallowed;
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
     * @param  overtimehoursallowed  DOCUMENT ME!
     */
    public void setOvertimehoursallowed(final boolean overtimehoursallowed) {
        this.overtimehoursallowed = overtimehoursallowed;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "project"
    )
    public Set<Project> getProjects() {
        return this.projects;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projects  DOCUMENT ME!
     */
    public void setProjects(final Set<Project> projects) {
        this.projects = projects;
    }
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "project"
    )
    public Set<ProjectPeriod> getProjectPeriods() {
        return this.projectPeriods;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projectPeriods  DOCUMENT ME!
     */
    public void setProjectPeriods(final Set<ProjectPeriod> projectPeriods) {
        this.projectPeriods = projectPeriods;
    }
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "project"
    )
    public Set<CostCategory> getCostCategories() {
        return this.costCategories;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  costCategories  DOCUMENT ME!
     */
    public void setCostCategories(final Set<CostCategory> costCategories) {
        this.costCategories = costCategories;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "project"
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
        fetch = FetchType.LAZY,
        mappedBy = "project"
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
     * @param   date  cal
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  if the given date is not within the most recent project period
     */
    public int getProjectQuarter(final Date date) throws IllegalArgumentException {
        final int projectQuarter = ((getProjectMonth(date) - 1) / 3);

        return projectQuarter + 1;
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
    public int getProjectMonth(final Date date) throws IllegalArgumentException {
        final ProjectPeriod period = QueryHelper.getMostRecentPeriod(this);
        final Date start = period.getFromdate();
        final int year = date.getYear() - start.getYear();
        final int months = date.getMonth() - start.getMonth();
        int day = 1;

        if (((period.getTodate() != null) && (date.getTime() > period.getTodate().getTime()))
                    || (date.getTime() < period.getFromdate().getTime())) {
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
        final ProjectPeriod mostRecentPeriod = QueryHelper.getMostRecentPeriod(this);
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
