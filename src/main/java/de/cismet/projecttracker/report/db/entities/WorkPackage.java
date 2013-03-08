package de.cismet.projecttracker.report.db.entities;

import de.cismet.projecttracker.report.commons.TimePeriod;
import de.cismet.projecttracker.report.helper.QueryHelper;
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

@Entity
@Table(name = "work_package", schema = "public")
public class WorkPackage extends BasicHibernateEntity {
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

    public WorkPackage() {
    }

    public WorkPackage(long id, Project project, Staff responsiblestaff, String abbreviation, String name) {
        this.id = id;
        this.responsiblestaff = responsiblestaff;
        this.project = project;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public WorkPackage(long id, Staff responsiblestaff, WorkPackage workPackage, Project project, String name, boolean issubversion,  double warnlevel, double criticallevel, double fullstoplevel, String abbreviation, String description, Set<WorkPackage> workPackages, Set<WorkPackagePeriod> workPackagePeriods, Set<Activity> activityWorkPackages, Set<ProjectComponentTag> projectComponentTags, Set<EstimatedComponentCost> estimatedWorkPackageCosts, CostCategory costCategory, Set<WorkPackageProgress> workPackageProgresses) {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentworkpackage")
    public WorkPackage getWorkPackage() {
        return this.workPackage;
    }

    public void setWorkPackage(WorkPackage workPackage) {
        this.workPackage = workPackage;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project", nullable = false)
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsiblestaff", nullable = true)
    public Staff getResponsiblestaff() {
        return this.responsiblestaff;
    }

    public void setResponsiblestaff(Staff responsiblestaff) {
        this.responsiblestaff = responsiblestaff;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "costcategoryid", nullable = false)
    public CostCategory getCostCategory() {
        return this.costCategory;
    }

    public void setCostCategory(CostCategory costCategory) {
        this.costCategory = costCategory;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "abbreviation", nullable = false, length = 50)
    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpirationDescription() {
        return expirationDescription;
    }

    public void setExpirationDescription(String expirationDescription) {
        this.expirationDescription = expirationDescription;
    }
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "workPackage")
    public Set<WorkPackage> getWorkPackages() {
        return this.workPackages;
    }

    public void setWorkPackages(Set<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "workPackage")
    public Set<WorkPackagePeriod> getWorkPackagePeriods() {
        return this.workPackagePeriods;
    }

    public void setWorkPackagePeriods(Set<WorkPackagePeriod> workPackagePeriods) {
        this.workPackagePeriods = workPackagePeriods;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "workPackage")
    public Set<Activity> getActivityWorkPackages() {
        return this.activityWorkPackages;
    }

    public void setActivityWorkPackages(Set<Activity> activityWorkPackages) {
        this.activityWorkPackages = activityWorkPackages;
    }

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="project_component_tag_project_component", schema="public", joinColumns = {
        @JoinColumn(name="projectcomponentid", nullable=false, updatable=false) }, inverseJoinColumns = {
        @JoinColumn(name="projectcomponenttagid", nullable=false, updatable=false) })
    public Set<ProjectComponentTag> getProjectComponentTags() {
        return this.projectComponentTags;
    }

    public void setProjectComponentTags(Set<ProjectComponentTag> projectComponentTags) {
        this.projectComponentTags = projectComponentTags;
    }

    @Column(name="warnlevel", nullable=true, precision=10)
    public double getWarnlevel() {
        return this.warnlevel;
    }

    public void setWarnlevel(double warnlevel) {
        this.warnlevel = warnlevel;
    }

    @Column(name="criticallevel", nullable=true, precision=10)
    public double getCriticallevel() {
        return this.criticallevel;
    }

    public void setCriticallevel(double criticallevel) {
        this.criticallevel = criticallevel;
    }

    @Column(name="fullstoplevel", nullable=true, precision=10)
    public double getFullstoplevel() {
        return this.fullstoplevel;
    }

    public void setFullstoplevel(double fullstoplevel) {
        this.fullstoplevel = fullstoplevel;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "workPackage")
    public Set<EstimatedComponentCost> getEstimatedWorkPackageCosts() {
        return this.estimatedWorkPackageCosts;
    }

    public void setEstimatedWorkPackageCosts(Set<EstimatedComponentCost> estimatedWorkPackageCosts) {
        this.estimatedWorkPackageCosts = estimatedWorkPackageCosts;
    }

    @Column(name="issubversion")
    public boolean getIssubversion() {
        return this.issubversion;
    }

    public void setIssubversion(boolean issubversion) {
        this.issubversion = issubversion;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="workPackage")
    public Set<WorkPackageProgress> getWorkPackageProgresses() {
        return this.workPackageProgresses;
    }

    public void setWorkPackageProgresses(Set<WorkPackageProgress> workPackageProgresses) {
        this.workPackageProgresses = workPackageProgresses;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WorkPackage) {
            WorkPackage other = (WorkPackage) obj;
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
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    /**
     *
     * @param cal
     * @return
     * @throws IllegalArgumentException if the given date is not within the most recent project period
     */
    public int getWorkPackageQuarter(Date date) throws IllegalArgumentException {
        int projectQuarter = ( (getWorkpackageMonth(date) - 1) / 3);

        return projectQuarter;
    }


    public int getWorkpackageMonth(Date date) throws IllegalArgumentException {
        WorkPackagePeriod period = QueryHelper.getMostRecentPeriod( this );
        Date start = period.getFromdate();
        int year = date.getYear() - start.getYear();
        int months = date.getMonth() - start.getMonth();
        int day = 1;

        if (date.getTime() > period.getTodate().getTime() || date.getTime() < period.getFromdate().getTime()) {
            throw new IllegalArgumentException();
        }

        if (date.getDate() < start.getDate()) {
            day = 0;
        }

        return months + (year * 12) + day;
    }

    
    public TimePeriod getTimeperiodOfMonth(int month) throws IllegalArgumentException {
        TimePeriod period = new TimePeriod();
        WorkPackagePeriod mostRecentPeriod = QueryHelper.getMostRecentPeriod( this );
        GregorianCalendar newBegin = new GregorianCalendar();
        GregorianCalendar end = new GregorianCalendar();

        newBegin.setTime( mostRecentPeriod.getFromdate() );
        end.setTime( mostRecentPeriod.getTodate() );
        newBegin.add( GregorianCalendar.MONTH, month - 1 );

        if (newBegin.after(end)) {
            throw new IllegalArgumentException();
        }

        GregorianCalendar newEnd = (GregorianCalendar)newBegin.clone();
        newEnd.add(GregorianCalendar.MONTH, 1);
        newEnd.add(GregorianCalendar.DATE, -1);
        period.setStart(newBegin);

        if ( end.after( end ) ) {
            period.setEnd(end);
        } else {
            period.setEnd(newEnd);
        }

        return period;
    }
}


