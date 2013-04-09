/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
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
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "project_costs",
    schema = "public"
)
public class ProjectCosts extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private CostCategory costCategory;
    private String comment;
    private double total;
    private Date time;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ProjectCosts object.
     */
    public ProjectCosts() {
    }

    /**
     * Creates a new ProjectCosts object.
     *
     * @param  id            DOCUMENT ME!
     * @param  costCategory  DOCUMENT ME!
     * @param  comment       DOCUMENT ME!
     * @param  total         DOCUMENT ME!
     * @param  time          DOCUMENT ME!
     */
    public ProjectCosts(final long id,
            final CostCategory costCategory,
            final String comment,
            final double total,
            final Date time) {
        this.id = id;
        this.costCategory = costCategory;
        this.comment = comment;
        this.total = total;
        this.time = time;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "projectcategoryid",
        nullable = false
    )
    public CostCategory getCostCategory() {
        return this.costCategory;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  costCategory  DOCUMENT ME!
     */
    public void setCostCategory(final CostCategory costCategory) {
        this.costCategory = costCategory;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "time",
        nullable = false,
        length = 29
    )
    public Date getTime() {
        return this.time;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  time  DOCUMENT ME!
     */
    public void setTime(final Date time) {
        this.time = time;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the comment
     */
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  comment  the comment to set
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the total
     */
    @Column(
        name = "total",
        precision = 10
    )
    public double getTotal() {
        return total;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  total  the total to set
     */
    public void setTotal(final double total) {
        this.total = total;
    }
}
