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
@Table(name = "estimated_work_package_cost", schema = "public")
public class EstimatedComponentCost extends BasicHibernateEntity {

    private WorkPackage workPackage;
    private Date creationtime;
    private Set<EstimatedComponentCostMonth> estimatedWorkPackageCostMonth = new HashSet<EstimatedComponentCostMonth>();

    public EstimatedComponentCost() {
    }

    public EstimatedComponentCost(long id, WorkPackage workPackage, Date creationtime, Set<EstimatedComponentCostMonth> estimatedWorkPackageCostMonth) {
        this.id = id;
        this.workPackage = workPackage;
        this.creationtime = creationtime;
        this.estimatedWorkPackageCostMonth = estimatedWorkPackageCostMonth;
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
    @Column(name = "creationtime", nullable = false, length = 29)
    public Date getCreationtime() {
        return this.creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "estimatedWorkPackageCost")
    public Set<EstimatedComponentCostMonth> getEstimatedWorkPackageCostMonth() {
        return this.estimatedWorkPackageCostMonth;
    }

    public void setEstimatedWorkPackageCostMonth(Set<EstimatedComponentCostMonth> estimatedWorkPackageCostMonth) {
        this.estimatedWorkPackageCostMonth = estimatedWorkPackageCostMonth;
    }

    /**
     *
     * @param month
     * @return the estimated work package cost for the given monthor an empty object,
     * if no estimation exists for the given month
     */
    public EstimatedComponentCostMonth getEstimatedCostForMonth(int month) {
        for (EstimatedComponentCostMonth tmp : estimatedWorkPackageCostMonth) {
            if (tmp.getMonth() == month) {
                return tmp;
            }
        }

        return new EstimatedComponentCostMonth();
    }
}


