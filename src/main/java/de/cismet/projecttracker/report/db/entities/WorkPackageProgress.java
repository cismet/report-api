package de.cismet.projecttracker.report.db.entities;
// Generated Feb 5, 2010 11:22:10 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WorkPackageProgress generated by hbm2java
 */
@Entity
@Table(name="work_package_progress"
    ,schema="public"
)
public class WorkPackageProgress extends BasicHibernateEntity {
     private WorkPackage workPackage;
     private Date time;
     private int progress;

    public WorkPackageProgress() {
    }

    public WorkPackageProgress(long id, WorkPackage workPackage, Date time, int progress) {
       this.id = id;
       this.workPackage = workPackage;
       this.time = time;
       this.progress = progress;
    }


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id", unique=true, nullable=false, insertable=false, updatable=false)
    public WorkPackage getWorkPackage() {
        return this.workPackage;
    }

    public void setWorkPackage(WorkPackage workPackage) {
        this.workPackage = workPackage;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time", nullable=false, length=29)
    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name="progress", nullable=false)
    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

