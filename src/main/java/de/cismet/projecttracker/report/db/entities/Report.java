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

@Entity
@Table(name = "report", schema = "public")
public class Report extends BasicHibernateEntity {

    private String name;
    private String generatorname;
    private Staff staff;
    private Date creationtime;
    private Date fromdate;
    private Date todate;
    private byte[] reportdocument;
    private Set<Activity> activities = new HashSet<Activity>(0);

    public Report() {
    }

    public Report(long id, String name, String generatorname, Date creationtime, Date fromdate, Date todate, byte[] reportdocument, Staff staff) {
        this.id = id;
        this.name = name;
        this.creationtime = creationtime;
        this.fromdate = fromdate;
        this.todate = todate;
        this.reportdocument = reportdocument;
        this.staff = staff;
        this.generatorname = generatorname;
    }

    public Report(long id, String name, String generatorname, Date creationtime, Date fromdate, Date todate, byte[] reportdocument, Set<Activity> activities, Staff staff) {
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

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="generatorname", nullable=false, length=50)
    public String getGeneratorname() {
        return this.generatorname;
    }

    public void setGeneratorname(String generatorname) {
        this.generatorname = generatorname;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "reportdocument", nullable = false)
    public byte[] getReportdocument() {
        return this.reportdocument;
    }

    public void setReportdocument(byte[] reportdocument) {
        this.reportdocument = reportdocument;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationtime", nullable = false, length = 29)
    public Date getCreationtime() {
        return this.creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fromdate", nullable = false, length = 29)
    public Date getFromdate() {
        return this.fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "todate", nullable = false, length = 29)
    public Date getTodate() {
        return this.todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staffid", nullable = true)
    public Staff getStaff() {
        return this.staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "activity_report", schema = "public", joinColumns = {
        @JoinColumn(name = "reportid", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "activityid", nullable = false, updatable = false)})
    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}


