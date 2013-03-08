package de.cismet.projecttracker.report.db.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company", schema = "public")
public class Company extends BasicHibernateEntity {
    private String name;
    private Set<Contract> contracts = new HashSet<Contract>(0);
    private Set<RealOverhead> realOverheads = new HashSet<RealOverhead>(0);
    private Set<Funding> fundings = new HashSet<Funding>(0);

    public Company() {
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(long id, String name, Set<Contract> contracts, Set<RealOverhead> realOverheads, Set<Funding> fundings) {
        this.id = id;
        this.name = name;
        this.contracts = contracts;
        this.realOverheads = realOverheads;
        this.fundings = fundings;
    }

    @Id
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
    public Set<Contract> getContracts() {
        return this.contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "company")
    public Set<RealOverhead> getRealOverheads() {
        return this.realOverheads;
    }

    public void setRealOverheads(Set<RealOverhead> realOverheads) {
        this.realOverheads = realOverheads;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
    public Set<Funding> getFundings() {
        return this.fundings;
    }

    public void setFundings(Set<Funding> fundings) {
        this.fundings = fundings;
    }
}


