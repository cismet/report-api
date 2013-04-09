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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="project"
    ,schema="public"
    , uniqueConstraints = @UniqueConstraint(columnNames="name")
)
public class Project extends BasicHibernateEntity {
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

    public Project() {
    }


    public Project(long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Project(long id, Staff responsiblestaff, ProjectBody projectBody, Project project, String name, double hoursofamanday, double daysofamanmonth, boolean overtimehoursallowed, String description, double overheadratio, double warnlevel, double criticallevel, double fullstoplevel, Set<Project> projects, Set<ProjectPeriod> projectPeriods, Set<CostCategory> costCategories, Set<WorkPackage> workPackages, Set<ProjectComponentTag> projectComponentTags, ProjectCategory projectCategory) {
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


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="projectbodyid")
    public ProjectBody getProjectBody() {
        return this.projectBody;
    }

    public void setProjectBody(ProjectBody projectBody) {
        this.projectBody = projectBody;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="categoryid")
    public ProjectCategory getProjectCategory() {
        return this.projectCategory;
    }

    public void setProjectCategory(ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="parentproject")
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

    @Column(name="name", unique=true, nullable=false, length=50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="overheadratio", precision=6)
    public double getOverheadratio() {
        return this.overheadratio;
    }

    public void setOverheadratio(double overheadratio) {
        this.overheadratio = overheadratio;
    }

    @Column(name="hoursofamanday", precision=6)
    public double getHoursofamanday() {
        return this.hoursofamanday;
    }

    public void setHoursofamanday(double hoursofamanday) {
        this.hoursofamanday = hoursofamanday;
    }

    @Column(name="daysofamanmonth", precision=6)
    public double getDaysofamanmonth() {
        return this.daysofamanmonth;
    }

    public void setDaysofamanmonth(double daysofamanmonth) {
        this.daysofamanmonth = daysofamanmonth;
    }

    @Column(name="overtimehoursallowed", precision=6)
    public boolean  getOvertimehoursallowed() {
        return this.overtimehoursallowed;
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

    public void setOvertimehoursallowed(boolean overtimehoursallowed) {
        this.overtimehoursallowed = overtimehoursallowed;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="project")
    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="project")
    public Set<ProjectPeriod> getProjectPeriods() {
        return this.projectPeriods;
    }

    public void setProjectPeriods(Set<ProjectPeriod> projectPeriods) {
        this.projectPeriods = projectPeriods;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="project")
    public Set<CostCategory> getCostCategories() {
        return this.costCategories;
    }

    public void setCostCategories(Set<CostCategory> costCategories) {
        this.costCategories = costCategories;
    }


    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="project")
    public Set<WorkPackage> getWorkPackages() {
        return this.workPackages;
    }

    public void setWorkPackages(Set<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="project")
    public Set<ProjectComponentTag> getProjectComponentTags() {
        return this.projectComponentTags;
    }

    public void setProjectComponentTags(Set<ProjectComponentTag> projectComponentTags) {
        this.projectComponentTags = projectComponentTags;
    }


    /**
     *
     * @param cal
     * @return
     * @throws IllegalArgumentException if the given date is not within the most recent project period
     */
    public int getProjectQuarter(Date date) throws IllegalArgumentException {
        int projectQuarter = ( (getProjectMonth(date) - 1) / 3);

        return projectQuarter + 1;
    }
    

    public int getProjectMonth(Date date) throws IllegalArgumentException {
        ProjectPeriod period = QueryHelper.getMostRecentPeriod( this );
        Date start = period.getFromdate();
        int year = date.getYear() - start.getYear();
        int months = date.getMonth() - start.getMonth();
        int day = 1;

        if ((period.getTodate() != null && date.getTime() > period.getTodate().getTime()) || date.getTime() < period.getFromdate().getTime()) {
            throw new IllegalArgumentException();
        }

        if (date.getDate() < start.getDate()) {
            day = 0;
        }

        return months + (year * 12) + day;
    }

    
    public TimePeriod getTimeperiodOfMonth(int month) throws IllegalArgumentException {
        TimePeriod period = new TimePeriod();
        ProjectPeriod mostRecentPeriod = QueryHelper.getMostRecentPeriod( this );
        GregorianCalendar newBegin = new GregorianCalendar();
        GregorianCalendar end = new GregorianCalendar();
        
        newBegin.setTime( mostRecentPeriod.getFromdate() );
        end.setTime( mostRecentPeriod.getTodate() );
        newBegin.add(GregorianCalendar.MONTH, month - 1);

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


