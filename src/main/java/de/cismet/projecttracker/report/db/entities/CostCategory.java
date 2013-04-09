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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "cost_category",
    schema = "public"
)
public class CostCategory extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private String name;
    private Project project;
    private String description;
    private double fundingrate;
    private double vat;
    private Set<ProjectCosts> projectCosts = new HashSet<ProjectCosts>(0);
    private Set<WorkPackage> workPackages = new HashSet<WorkPackage>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CostCategory object.
     */
    public CostCategory() {
    }

    /**
     * Creates a new CostCategory object.
     *
     * @param  id           DOCUMENT ME!
     * @param  name         DOCUMENT ME!
     * @param  project      DOCUMENT ME!
     * @param  description  DOCUMENT ME!
     * @param  fundingrate  DOCUMENT ME!
     * @param  vat          DOCUMENT ME!
     */
    public CostCategory(final long id,
            final String name,
            final Project project,
            final String description,
            final double fundingrate,
            final double vat) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.description = description;
        this.fundingrate = fundingrate;
        this.vat = vat;
    }

    /**
     * Creates a new CostCategory object.
     *
     * @param  id            DOCUMENT ME!
     * @param  name          DOCUMENT ME!
     * @param  project       DOCUMENT ME!
     * @param  description   DOCUMENT ME!
     * @param  fundingrate   DOCUMENT ME!
     * @param  vat           DOCUMENT ME!
     * @param  projectCosts  DOCUMENT ME!
     * @param  workPackages  DOCUMENT ME!
     */
    public CostCategory(final long id,
            final String name,
            final Project project,
            final String description,
            final double fundingrate,
            final double vat,
            final Set<ProjectCosts> projectCosts,
            final Set<WorkPackage> workPackages) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.description = description;
        this.fundingrate = fundingrate;
        this.projectCosts = projectCosts;
        this.workPackages = workPackages;
        this.vat = vat;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "projectid",
        nullable = false
    )
    public Project getProject() {
        return this.project;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  project  DOCUMENT ME!
     */
    public void setProject(final Project project) {
        this.project = project;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "description",
        nullable = true
    )
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "name",
        nullable = false
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
    @Column(
        name = "fundingrate",
        nullable = false,
        precision = 6
    )
    public double getFundingrate() {
        return this.fundingrate;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fundingrate  DOCUMENT ME!
     */
    public void setFundingrate(final double fundingrate) {
        this.fundingrate = fundingrate;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "vat",
        nullable = false,
        precision = 4
    )
    public double getVat() {
        return this.vat;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  vat  DOCUMENT ME!
     */
    public void setVat(final double vat) {
        this.vat = vat;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "cost_category"
    )
    public Set<ProjectCosts> getProjectCosts() {
        return this.projectCosts;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projectCosts  DOCUMENT ME!
     */
    public void setProjectCosts(final Set<ProjectCosts> projectCosts) {
        this.projectCosts = projectCosts;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "cost_category"
    )
    public Set<WorkPackage> getWorkPackages() {
        return this.workPackages;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workPackages  DOCUMENT ME!
     */
    public void setWorkPackages(final Set<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }
}
