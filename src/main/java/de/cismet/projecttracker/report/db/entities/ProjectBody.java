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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "project_body",
    schema = "public"
)
public class ProjectBody extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private String name;
    private Set<Project> projects = new HashSet<Project>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ProjectBody object.
     */
    public ProjectBody() {
    }

    /**
     * Creates a new ProjectBody object.
     *
     * @param  id  DOCUMENT ME!
     */
    public ProjectBody(final long id) {
        this.id = id;
    }

    /**
     * Creates a new ProjectBody object.
     *
     * @param  id        DOCUMENT ME!
     * @param  name      DOCUMENT ME!
     * @param  projects  DOCUMENT ME!
     */
    public ProjectBody(final long id, final String name, final Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "name",
        length = 50
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
        fetch = FetchType.EAGER,
        mappedBy = "projectBody"
    )
    public Set<Project> getProjects() {
        return this.projects;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projects  DOCUMENT ME!
     */
    public void setProjects(final Set<Project> projects) {
        this.projects = projects;
    }
}
