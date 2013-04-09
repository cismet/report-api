/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
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

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "company",
    schema = "public"
)
public class Company extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private String name;
    private Set<Contract> contracts = new HashSet<Contract>(0);
    private Set<RealOverhead> realOverheads = new HashSet<RealOverhead>(0);
    private Set<Funding> fundings = new HashSet<Funding>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Company object.
     */
    public Company() {
    }

    /**
     * Creates a new Company object.
     *
     * @param  id    DOCUMENT ME!
     * @param  name  DOCUMENT ME!
     */
    public Company(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Creates a new Company object.
     *
     * @param  id             DOCUMENT ME!
     * @param  name           DOCUMENT ME!
     * @param  contracts      DOCUMENT ME!
     * @param  realOverheads  DOCUMENT ME!
     * @param  fundings       DOCUMENT ME!
     */
    public Company(final long id,
            final String name,
            final Set<Contract> contracts,
            final Set<RealOverhead> realOverheads,
            final Set<Funding> fundings) {
        this.id = id;
        this.name = name;
        this.contracts = contracts;
        this.realOverheads = realOverheads;
        this.fundings = fundings;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Id
    @Column(
        name = "name",
        nullable = false,
        length = 100
    )
    public String getName() {
        return this.name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  name  DOCUMENT ME!
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "company"
    )
    public Set<Contract> getContracts() {
        return this.contracts;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  contracts  DOCUMENT ME!
     */
    public void setContracts(final Set<Contract> contracts) {
        this.contracts = contracts;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "company"
    )
    public Set<RealOverhead> getRealOverheads() {
        return this.realOverheads;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  realOverheads  DOCUMENT ME!
     */
    public void setRealOverheads(final Set<RealOverhead> realOverheads) {
        this.realOverheads = realOverheads;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "company"
    )
    public Set<Funding> getFundings() {
        return this.fundings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fundings  DOCUMENT ME!
     */
    public void setFundings(final Set<Funding> fundings) {
        this.fundings = fundings;
    }
}
