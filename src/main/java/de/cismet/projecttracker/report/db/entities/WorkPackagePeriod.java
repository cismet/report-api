package de.cismet.projecttracker.report.db.entities;

import java.util.Date;
import java.util.GregorianCalendar;
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
@Table(name = "work_package_period", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "asof"))
public class WorkPackagePeriod extends BasicHibernateEntity implements Comparable<Object> {
    private WorkPackage workPackage;
    private Date fromdate;
    private Date todate;
    private Date asof;

    public WorkPackagePeriod() {
    }

    public WorkPackagePeriod(long id, WorkPackage workPackage, Date fromdate, Date asof) {
        this.id = id;
        this.workPackage = workPackage;
        this.fromdate = fromdate;
        this.asof = asof;
    }

    public WorkPackagePeriod(long id, WorkPackage workPackage, Date fromdate, Date todate, Date asof) {
        this.id = id;
        this.workPackage = workPackage;
        this.fromdate = fromdate;
        this.todate = todate;
        this.asof = asof;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workpackageid", nullable = false)
    public WorkPackage getWorkPackage() {
        return this.workPackage;
    }

    public void setWorkPackage(WorkPackage workPackage) {
        this.workPackage = workPackage;
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

    @Override
    public int compareTo(Object o) {
        if (o instanceof WorkPackagePeriod) {
            if (o == null || ((WorkPackagePeriod)o).asof == null) {
                return -1;
            }
            if (asof == null) {
                return 1;
            }
            long result = asof.getTime() - ((WorkPackagePeriod) o).asof.getTime();
            return (int) Math.signum(result);
        } else if (o instanceof Date) {
            long result = asof.getTime() - ((Date) o).getTime();
            return (int) Math.signum(result);
        } else if (o instanceof GregorianCalendar) {
            long result = asof.getTime() - ((GregorianCalendar) o).getTime().getTime();
            return (int) Math.signum(result);
        } else {
            return 0;
        }
    }
}


