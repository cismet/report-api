/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
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

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "estimated_work_package_cost",
    schema = "public"
)
public class EstimatedComponentCost extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private WorkPackage workPackage;
    private Date creationtime;
    private Set<EstimatedComponentCostMonth> estimatedWorkPackageCostMonth = new HashSet<EstimatedComponentCostMonth>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EstimatedComponentCost object.
     */
    public EstimatedComponentCost() {
    }

    /**
     * Creates a new EstimatedComponentCost object.
     *
     * @param  id                             DOCUMENT ME!
     * @param  workPackage                    DOCUMENT ME!
     * @param  creationtime                   DOCUMENT ME!
     * @param  estimatedWorkPackageCostMonth  DOCUMENT ME!
     */
    public EstimatedComponentCost(final long id,
            final WorkPackage workPackage,
            final Date creationtime,
            final Set<EstimatedComponentCostMonth> estimatedWorkPackageCostMonth) {
        this.id = id;
        this.workPackage = workPackage;
        this.creationtime = creationtime;
        this.estimatedWorkPackageCostMonth = estimatedWorkPackageCostMonth;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "workpackageid",
        nullable = false
    )
    public WorkPackage getWorkPackage() {
        return this.workPackage;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workPackage  DOCUMENT ME!
     */
    public void setWorkPackage(final WorkPackage workPackage) {
        this.workPackage = workPackage;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "creationtime",
        nullable = false,
        length = 29
    )
    public Date getCreationtime() {
        return this.creationtime;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  creationtime  DOCUMENT ME!
     */
    public void setCreationtime(final Date creationtime) {
        this.creationtime = creationtime;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "estimatedWorkPackageCost"
    )
    public Set<EstimatedComponentCostMonth> getEstimatedWorkPackageCostMonth() {
        return this.estimatedWorkPackageCostMonth;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  estimatedWorkPackageCostMonth  DOCUMENT ME!
     */
    public void setEstimatedWorkPackageCostMonth(final Set<EstimatedComponentCostMonth> estimatedWorkPackageCostMonth) {
        this.estimatedWorkPackageCostMonth = estimatedWorkPackageCostMonth;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   month  DOCUMENT ME!
     *
     * @return  the estimated work package cost for the given monthor an empty object, if no estimation exists for the
     *          given month
     */
    public EstimatedComponentCostMonth getEstimatedCostForMonth(final int month) {
        for (final EstimatedComponentCostMonth tmp : estimatedWorkPackageCostMonth) {
            if (tmp.getMonth() == month) {
                return tmp;
            }
        }

        return new EstimatedComponentCostMonth();
    }
}
