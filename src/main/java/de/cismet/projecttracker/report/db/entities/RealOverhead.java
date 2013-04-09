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
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "real_overhead",
    schema = "public"
)
public class RealOverhead extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private Company company;
    private double overheadratio;
    private Date validfrom;
    private Date validto;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new RealOverhead object.
     */
    public RealOverhead() {
    }

    /**
     * Creates a new RealOverhead object.
     *
     * @param  id             DOCUMENT ME!
     * @param  company        DOCUMENT ME!
     * @param  overheadratio  DOCUMENT ME!
     * @param  validfrom      DOCUMENT ME!
     */
    public RealOverhead(final long id, final Company company, final double overheadratio, final Date validfrom) {
        this.id = id;
        this.company = company;
        this.overheadratio = overheadratio;
        this.validfrom = validfrom;
    }

    /**
     * Creates a new RealOverhead object.
     *
     * @param  id             DOCUMENT ME!
     * @param  company        DOCUMENT ME!
     * @param  overheadratio  DOCUMENT ME!
     * @param  validfrom      DOCUMENT ME!
     * @param  validto        DOCUMENT ME!
     */
    public RealOverhead(final long id,
            final Company company,
            final double overheadratio,
            final Date validfrom,
            final Date validto) {
        this.id = id;
        this.company = company;
        this.overheadratio = overheadratio;
        this.validfrom = validfrom;
        this.validto = validto;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "companyid",
        nullable = false
    )
    public Company getCompany() {
        return this.company;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  company  DOCUMENT ME!
     */
    public void setCompany(final Company company) {
        this.company = company;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "overheadratio",
        nullable = false
    )
    public double getOverheadratio() {
        return this.overheadratio;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  overheadratio  DOCUMENT ME!
     */
    public void setOverheadratio(final double overheadratio) {
        this.overheadratio = overheadratio;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "validfrom",
        nullable = false,
        length = 29
    )
    public Date getValidfrom() {
        return this.validfrom;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  validfrom  DOCUMENT ME!
     */
    public void setValidfrom(final Date validfrom) {
        this.validfrom = validfrom;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "validto",
        length = 29
    )
    public Date getValidto() {
        return this.validto;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  validto  DOCUMENT ME!
     */
    public void setValidto(final Date validto) {
        this.validto = validto;
    }
}
