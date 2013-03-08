package de.cismet.projecttracker.report.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author therter
 */
@Entity
@Table(name="estimated_work_package_cost_month"
    ,schema="public")
public class EstimatedComponentCostMonth extends BasicHibernateEntity {
    private EstimatedComponentCost estimatedWorkPackageCost;
    private int month;
    private double workinghours;
    private String description;

    public EstimatedComponentCostMonth() {
    }

    public EstimatedComponentCostMonth(long id, EstimatedComponentCost estimatedWorkPackageCost, int month, double workinghours, String description) {
        this.id = id;
        this.estimatedWorkPackageCost = estimatedWorkPackageCost;
        this.month = month;
        this.workinghours = workinghours;
        this.description = description;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="estimatedWorkPackageCost", nullable=false)
    public EstimatedComponentCost getEstimatedWorkPackageCost() {
        return this.estimatedWorkPackageCost;
    }

    public void setEstimatedWorkPackageCost(EstimatedComponentCost estimatedWorkPackageCost) {
        this.estimatedWorkPackageCost = estimatedWorkPackageCost;
    }


    @Column(name="month", nullable=false)
    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Column(name="workinghours", nullable=false, precision=10)
    public double getWorkinghours() {
        return this.workinghours;
    }

    public void setWorkinghours(double workinghours) {
        this.workinghours = workinghours;
    }

    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
