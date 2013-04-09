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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "project_component_tag",
    schema = "public"
)
public class ProjectComponentTag extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private Project project;
    private String name;
    private Set<WorkPackage> workPackages = new HashSet<WorkPackage>(0);
    private Set<ProjectComponentTag> projectComponentTagsForProjectcomponenttagdest = new HashSet<ProjectComponentTag>(
            0);
//     private Set<ProjectComponentTag> projectComponentTagsForProjectcomponenttagsource = new HashSet<ProjectComponentTag>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ProjectComponentTag object.
     */
    public ProjectComponentTag() {
    }

    /**
     * Creates a new ProjectComponentTag object.
     *
     * @param  id       DOCUMENT ME!
     * @param  project  DOCUMENT ME!
     * @param  name     DOCUMENT ME!
     */
    public ProjectComponentTag(final long id, final Project project, final String name) {
        this.id = id;
        this.project = project;
        this.name = name;
    }
    /**
     * Creates a new ProjectComponentTag object.
     *
     * @param  id                                              DOCUMENT ME!
     * @param  project                                         DOCUMENT ME!
     * @param  name                                            DOCUMENT ME!
     * @param  workPackages                                    DOCUMENT ME!
     * @param  projectComponentTagsForProjectcomponenttagdest  DOCUMENT ME!
     */
    public ProjectComponentTag(final long id,
            final Project project,
            final String name,
            final Set<WorkPackage> workPackages,
            final Set<ProjectComponentTag> projectComponentTagsForProjectcomponenttagdest) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.workPackages = workPackages;
        this.projectComponentTagsForProjectcomponenttagdest = projectComponentTagsForProjectcomponenttagdest;
//       this.projectComponentTagsForProjectcomponenttagsource = projectComponentTagsForProjectcomponenttagsource;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.LAZY)
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
        name = "name",
        nullable = false,
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
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "project_component_tag_project_component",
        schema = "public",
        joinColumns = {
                @JoinColumn(
                    name = "projectcomponenttagid",
                    nullable = false,
                    updatable = false
                )
            },
        inverseJoinColumns = {
                @JoinColumn(
                    name = "projectcomponentid",
                    nullable = false,
                    updatable = false
                )
            }
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "compatible_tags",
        schema = "public",
        joinColumns = {
                @JoinColumn(
                    name = "projectcomponenttagsource",
                    nullable = false,
                    updatable = false
                )
            },
        inverseJoinColumns = {
                @JoinColumn(
                    name = "projectcomponenttagdest",
                    nullable = false,
                    updatable = false
                )
            }
    )
    public Set<ProjectComponentTag> getProjectComponentTagsForProjectcomponenttagdest() {
        return this.projectComponentTagsForProjectcomponenttagdest;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  projectComponentTagsForProjectcomponenttagdest  DOCUMENT ME!
     */
    public void setProjectComponentTagsForProjectcomponenttagdest(
            final Set<ProjectComponentTag> projectComponentTagsForProjectcomponenttagdest) {
        this.projectComponentTagsForProjectcomponenttagdest = projectComponentTagsForProjectcomponenttagdest;
    }
}
