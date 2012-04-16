package de.cismet.projecttracker.report.db.entities;

import de.cismet.projecttracker.report.helper.CalendarHelper;
import java.sql.Timestamp;
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

@Entity
@Table(name = "activity", schema = "public")
public class Activity extends BasicHibernateEntity {

    private Staff staff;
    private WorkPackage workPackage;
    private WorkCategory workCategory;
    private double workinghours;
    private boolean committed = false;
    private String description;
    private Date day;
    private int kindofactivity;
    private Set<Report> reports = new HashSet<Report>(0);

    public Activity() {
    }

    public Activity(long id, Date day, Staff staff, WorkPackage workPackage, double workinghours, String description, boolean committed, int kindofactivity) {
        this.id = id;
        this.day = day;
        this.staff = staff;
        this.workPackage = workPackage;
        this.workinghours = workinghours;
        this.description = description;
        this.committed = committed;
        this.kindofactivity = kindofactivity;
    }

    public Activity(long id, Date day, Staff staff, WorkPackage workPackage, double workinghours, String description, boolean committed, int kindofactivity, WorkCategory workCategory, Set<Report> reports) {
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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workpackageid", nullable = true)
    public WorkPackage getWorkPackage() {
        return this.workPackage;
    }

    public void setWorkPackage(WorkPackage workPackage) {
        this.workPackage = workPackage;
    }

    @Column(name = "workinghours", nullable = false, precision = 6)
    public double getWorkinghours() {
        return this.workinghours;
    }

    public void setWorkinghours(double workinghours) {
        this.workinghours = workinghours;
    }

    @Column(name = "description", nullable = true)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name = "committed")
    public boolean getCommitted() {
        return committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    @Column(name="kindofactivity", nullable=false)
    public int getKindofactivity() {
        return this.kindofactivity;
    }

    public void setKindofactivity(int kindofactivity) {
        this.kindofactivity = kindofactivity;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "day", nullable = false, length = 29)
    public Date getDay() {
        return this.day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staffid", nullable = false)
    public Staff getStaff() {
        return this.staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryid", nullable= true)
    public WorkCategory getWorkCategory() {
        return this.workCategory;
    }

    public void setWorkCategory(WorkCategory workCategory) {
        this.workCategory = workCategory;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "activity_report", schema = "public", joinColumns = {
        @JoinColumn(name = "activityid", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "reportid", nullable = false, updatable = false)})
    public Set<Report> getReports() {
        return this.reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    @Override
    public String toString() {
        return CalendarHelper.toDateString( day ) + "\n" + workPackage.getName() + "\n" + description + "\n" + workinghours;
    }
    
    
}


