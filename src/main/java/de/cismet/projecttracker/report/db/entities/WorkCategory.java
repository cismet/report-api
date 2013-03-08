package de.cismet.projecttracker.report.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "work_category", schema = "public")
public class WorkCategory extends BasicHibernateEntity {
    private String name;
    private boolean workpackagerelated = true;

    public WorkCategory() {
    }

    public WorkCategory(long id) {
        this.id = id;
    }

    public WorkCategory(long id, String name, boolean workpackagerelated) {
        this.id = id;
        this.name = name;
        this.workpackagerelated = workpackagerelated;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "workpackagerelated")
    public boolean getWorkpackagerelated() {
        return workpackagerelated;
    }

    public void setWorkpackagerelated(boolean workpackagerelated) {
        this.workpackagerelated = workpackagerelated;
    }
}
