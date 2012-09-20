/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.projecttracker.report.db.entities;

import javax.persistence.*;

/**
 *
 * @author dmeiers
 */
@Entity
@Table(name = "profile", schema = "public")
public class Profile extends BasicHibernateEntity {

    private boolean autoPauseEnabled;
    private boolean weekLockMode;
    
    public Profile(){
        
    }

    public Profile( boolean autoPauseEnabled, boolean weekLockMode) {
        this.autoPauseEnabled = autoPauseEnabled;
        this.weekLockMode = weekLockMode;
    }

    @Column(name = "auto_pause", nullable = false)
    public boolean getAutoPauseEnabled() {
        return autoPauseEnabled;
    }

    public void setAutoPauseEnabled(boolean autoPauseEnabled) {
        this.autoPauseEnabled = autoPauseEnabled;
    }

    @Column(name = "week_lock", nullable = false)
    public boolean getWeekLockMode() {
        return weekLockMode;
    }

    public void setWeekLockMode(boolean weekLockMode) {
        this.weekLockMode = weekLockMode;
    }

}
