package de.cismet.projecttracker.report.db.entities;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "contract", schema = "public")
public class Contract extends BasicHibernateEntity {

    private Staff staff;
    private Company company;
    private Date fromdate;
    private Date todate;
    private double whow;
    private int vacation;
    private Set<ContractDocument> contractDocuments = new HashSet<ContractDocument>(0);

    public Contract() {
    }

    public Contract(long id, Staff staff, Company company, Date fromdate, double whow) {
        this.id = id;
        this.staff = staff;
        this.company = company;
        this.fromdate = fromdate;
        this.whow = whow;
    }

    public Contract(long id, Staff staff, Company company, Date fromdate, Date todate, double whow, Set<ContractDocument> contractDocuments) {
        this.id = id;
        this.staff = staff;
        this.company = company;
        this.fromdate = fromdate;
        this.todate = todate;
        this.whow = whow;
        this.contractDocuments = contractDocuments;
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
    @JoinColumn(name = "company", nullable = false)
    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    @Column(name = "whow", nullable = false, precision = 5)
    public double getWhow() {
        return this.whow;
    }

    public void setWhow(double whow) {
        this.whow = whow;
    }
    
    @Column(name = "vacation", nullable = true)
    public int getVacation(){
        return this.vacation;
    }
    
    public void setVacation(int vacation){
        this.vacation = vacation;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contract")
    public Set<ContractDocument> getContractDocuments() {
        return this.contractDocuments;
    }

    public void setContractDocuments(Set<ContractDocument> contractDocuments) {
        this.contractDocuments = contractDocuments;
    }
}


