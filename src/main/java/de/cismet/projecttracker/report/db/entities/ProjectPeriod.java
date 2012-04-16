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

@Entity
@Table(name = "project_period", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "asof"))
public class ProjectPeriod extends BasicHibernateEntity {
    private Project project;
    private Date fromdate;
    private Date todate;
    private Date asof;

    public ProjectPeriod() {
    }

    public ProjectPeriod(long id, Project project, Date fromdate, Date asof) {
        this.id = id;
        this.project = project;
        this.fromdate = fromdate;
        this.asof = asof;
    }

    public ProjectPeriod(long id, Project project, Date fromdate, Date todate, Date asof) {
        this.id = id;
        this.project = project;
        this.fromdate = fromdate;
        this.todate = todate;
        this.asof = asof;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectid", nullable = false)
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
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
    @Column(name = "todate", length = 29)
    public Date getTodate() {
        return this.todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "asof", unique = true, nullable = false, length = 29)
    public Date getAsof() {
        return this.asof;
    }

    public void setAsof(Date asof) {
        this.asof = asof;
    }
}


