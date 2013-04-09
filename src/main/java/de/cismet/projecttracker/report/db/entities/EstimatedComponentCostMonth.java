/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "estimated_work_package_cost_month",
    schema = "public"
)
public class EstimatedComponentCostMonth extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private EstimatedComponentCost estimatedWorkPackageCost;
    private int month;
    private double workinghours;
    private String description;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EstimatedComponentCostMonth object.
     */
    public EstimatedComponentCostMonth() {
    }

    /**
     * Creates a new EstimatedComponentCostMonth object.
     *
     * @param  id                        DOCUMENT ME!
     * @param  estimatedWorkPackageCost  DOCUMENT ME!
     * @param  month                     DOCUMENT ME!
     * @param  workinghours              DOCUMENT ME!
     * @param  description               DOCUMENT ME!
     */
    public EstimatedComponentCostMonth(final long id,
            final EstimatedComponentCost estimatedWorkPackageCost,
            final int month,
            final double workinghours,
            final String description) {
        this.id = id;
        this.estimatedWorkPackageCost = estimatedWorkPackageCost;
        this.month = month;
        this.workinghours = workinghours;
        this.description = description;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "estimatedWorkPackageCost",
        nullable = false
    )
    public EstimatedComponentCost getEstimatedWorkPackageCost() {
        return this.estimatedWorkPackageCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  estimatedWorkPackageCost  DOCUMENT ME!
     */
    public void setEstimatedWorkPackageCost(final EstimatedComponentCost estimatedWorkPackageCost) {
        this.estimatedWorkPackageCost = estimatedWorkPackageCost;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "month",
        nullable = false
    )
    public int getMonth() {
        return this.month;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  month  DOCUMENT ME!
     */
    public void setMonth(final int month) {
        this.month = month;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "workinghours",
        nullable = false,
        precision = 10
    )
    public double getWorkinghours() {
        return this.workinghours;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workinghours  DOCUMENT ME!
     */
    public void setWorkinghours(final double workinghours) {
        this.workinghours = workinghours;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  description  DOCUMENT ME!
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
