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

@Entity
@Table(name = "real_overhead", schema = "public")
public class RealOverhead extends BasicHibernateEntity {
    private Company company;
    private double overheadratio;
    private Date validfrom;
    private Date validto;

    public RealOverhead() {
    }

    public RealOverhead(long id, Company company, double overheadratio, Date validfrom) {
        this.id = id;
        this.company = company;
        this.overheadratio = overheadratio;
        this.validfrom = validfrom;
    }

    public RealOverhead(long id, Company company, double overheadratio, Date validfrom, Date validto) {
        this.id = id;
        this.company = company;
        this.overheadratio = overheadratio;
        this.validfrom = validfrom;
        this.validto = validto;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyid", nullable = false)
    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Column(name = "overheadratio", nullable = false)
    public double getOverheadratio() {
        return this.overheadratio;
    }

    public void setOverheadratio(double overheadratio) {
        this.overheadratio = overheadratio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "validfrom", nullable = false, length = 29)
    public Date getValidfrom() {
        return this.validfrom;
    }

    public void setValidfrom(Date validfrom) {
        this.validfrom = validfrom;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "validto", length = 29)
    public Date getValidto() {
        return this.validto;
    }

    public void setValidto(Date validto) {
        this.validto = validto;
    }
}


