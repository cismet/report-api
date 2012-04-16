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


@Entity
@Table(name = "project_component_tag", schema = "public")
public class ProjectComponentTag extends BasicHibernateEntity {
     private Project project;
     private String name;
     private Set<WorkPackage> workPackages = new HashSet<WorkPackage>(0);
     private Set<ProjectComponentTag> projectComponentTagsForProjectcomponenttagdest = new HashSet<ProjectComponentTag>(0);
//     private Set<ProjectComponentTag> projectComponentTagsForProjectcomponenttagsource = new HashSet<ProjectComponentTag>(0);

    public ProjectComponentTag() {
    }


    public ProjectComponentTag(long id, Project project, String name) {
        this.id = id;
        this.project = project;
        this.name = name;
    }
    public ProjectComponentTag(long id, Project project, String name, Set<WorkPackage> workPackages, Set<ProjectComponentTag> projectComponentTagsForProjectcomponenttagdest) {
       this.id = id;
       this.project = project;
       this.name = name;
       this.workPackages = workPackages;
       this.projectComponentTagsForProjectcomponenttagdest = projectComponentTagsForProjectcomponenttagdest;
//       this.projectComponentTagsForProjectcomponenttagsource = projectComponentTagsForProjectcomponenttagsource;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="projectid", nullable=false)
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name="name", nullable=false, length=50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="project_component_tag_project_component", schema="public", joinColumns = {
        @JoinColumn(name="projectcomponenttagid", nullable=false, updatable=false) }, inverseJoinColumns = {
        @JoinColumn(name="projectcomponentid", nullable=false, updatable=false) })
    public Set<WorkPackage> getWorkPackages() {
        return this.workPackages;
    }

    public void setWorkPackages(Set<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="compatible_tags", schema="public", joinColumns = {
        @JoinColumn(name="projectcomponenttagsource", nullable=false, updatable=false) }, inverseJoinColumns = {
        @JoinColumn(name="projectcomponenttagdest", nullable=false, updatable=false) })
    public Set<ProjectComponentTag> getProjectComponentTagsForProjectcomponenttagdest() {
        return this.projectComponentTagsForProjectcomponenttagdest;
    }

    public void setProjectComponentTagsForProjectcomponenttagdest(Set<ProjectComponentTag> projectComponentTagsForProjectcomponenttagdest) {
        this.projectComponentTagsForProjectcomponenttagdest = projectComponentTagsForProjectcomponenttagdest;
    }
}
