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

/**
 *
 * @author therter
 */
@Entity
@Table(name = "project_costs", schema = "public")
public class ProjectCosts extends BasicHibernateEntity {
    private CostCategory costCategory;
    private String comment;
    private double total;
    private Date time;

    public ProjectCosts() {
    }

    public ProjectCosts(long id, CostCategory costCategory, String comment, double total, Date time) {
        this.id = id;
        this.costCategory = costCategory;
        this.comment = comment;
        this.total = total;
        this.time = time;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectcategoryid", nullable = false)
    public CostCategory getCostCategory() {
        return this.costCategory;
    }

    public void setCostCategory(CostCategory costCategory) {
        this.costCategory = costCategory;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time", nullable = false, length = 29)
    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the comment
     */
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the total
     */
    @Column(name = "total", precision = 10)
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }
}
