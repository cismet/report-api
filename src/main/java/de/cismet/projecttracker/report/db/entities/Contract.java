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
    name = "contract",
    schema = "public"
)
public class Contract extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private Staff staff;
    private Company company;
    private Date fromdate;
    private Date todate;
    private double whow;
    private int vacation;
    private Set<ContractDocument> contractDocuments = new HashSet<ContractDocument>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Contract object.
     */
    public Contract() {
    }

    /**
     * Creates a new Contract object.
     *
     * @param  id        DOCUMENT ME!
     * @param  staff     DOCUMENT ME!
     * @param  company   DOCUMENT ME!
     * @param  fromdate  DOCUMENT ME!
     * @param  whow      DOCUMENT ME!
     */
    public Contract(final long id, final Staff staff, final Company company, final Date fromdate, final double whow) {
        this.id = id;
        this.staff = staff;
        this.company = company;
        this.fromdate = fromdate;
        this.whow = whow;
    }

    /**
     * Creates a new Contract object.
     *
     * @param  id                 DOCUMENT ME!
     * @param  staff              DOCUMENT ME!
     * @param  company            DOCUMENT ME!
     * @param  fromdate           DOCUMENT ME!
     * @param  todate             DOCUMENT ME!
     * @param  whow               DOCUMENT ME!
     * @param  contractDocuments  DOCUMENT ME!
     */
    public Contract(final long id,
            final Staff staff,
            final Company company,
            final Date fromdate,
            final Date todate,
            final double whow,
            final Set<ContractDocument> contractDocuments) {
        this.id = id;
        this.staff = staff;
        this.company = company;
        this.fromdate = fromdate;
        this.todate = todate;
        this.whow = whow;
        this.contractDocuments = contractDocuments;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "staffid",
        nullable = false
    )
    public Staff getStaff() {
        return this.staff;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  staff  DOCUMENT ME!
     */
    public void setStaff(final Staff staff) {
        this.staff = staff;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "company",
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "fromdate",
        nullable = false,
        length = 29
    )
    public Date getFromdate() {
        return this.fromdate;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fromdate  DOCUMENT ME!
     */
    public void setFromdate(final Date fromdate) {
        this.fromdate = fromdate;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "todate",
        length = 29
    )
    public Date getTodate() {
        return this.todate;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  todate  DOCUMENT ME!
     */
    public void setTodate(final Date todate) {
        this.todate = todate;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "whow",
        nullable = false,
        precision = 5
    )
    public double getWhow() {
        return this.whow;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  whow  DOCUMENT ME!
     */
    public void setWhow(final double whow) {
        this.whow = whow;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "vacation",
        nullable = true
    )
    public int getVacation() {
        return this.vacation;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  vacation  DOCUMENT ME!
     */
    public void setVacation(final int vacation) {
        this.vacation = vacation;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "contract"
    )
    public Set<ContractDocument> getContractDocuments() {
        return this.contractDocuments;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  contractDocuments  DOCUMENT ME!
     */
    public void setContractDocuments(final Set<ContractDocument> contractDocuments) {
        this.contractDocuments = contractDocuments;
    }
}
