package de.cismet.projecttracker.report.db.entities;

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

@Entity
@Table(name = "cost_category", schema = "public")
public class CostCategory extends BasicHibernateEntity {
    private String name;
    private Project project;
    private String description;
    private double fundingrate;
    private double vat;
    private Set<ProjectCosts> projectCosts = new HashSet<ProjectCosts>(0);
    private Set<WorkPackage> workPackages = new HashSet<WorkPackage>(0);

    public CostCategory() {
    }

    public CostCategory(long id, String name, Project project, String description, double fundingrate, double vat) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.description = description;
        this.fundingrate = fundingrate;
        this.vat = vat;
    }

    public CostCategory(long id, String name, Project project, String description, double fundingrate, double vat, Set<ProjectCosts> projectCosts, Set<WorkPackage> workPackages) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.description = description;
        this.fundingrate = fundingrate;
        this.projectCosts = projectCosts;
        this.workPackages = workPackages;
        this.vat = vat;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectid", nullable = false)
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "description", nullable = true)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "fundingrate", nullable = false, precision = 6)
    public double getFundingrate() {
        return this.fundingrate;
    }

    public void setFundingrate(double fundingrate) {
        this.fundingrate = fundingrate;
    }

    @Column(name = "vat", nullable = false, precision = 4)
    public double getVat() {
        return this.vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cost_category")
    public Set<ProjectCosts> getProjectCosts() {
        return this.projectCosts;
    }

    public void setProjectCosts(Set<ProjectCosts> projectCosts) {
        this.projectCosts = projectCosts;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cost_category")
    public Set<WorkPackage> getWorkPackages() {
        return this.workPackages;
    }

    public void setWorkPackages(Set<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }
}


