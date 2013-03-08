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
    private boolean weekLockModeEnabled;
    private boolean dayLockModeEnabled;
    private double autoPauseDuration;
    private double residualVacation;
    
    public Profile(){
        
    }

    public Profile( boolean autoPauseEnabled, boolean weekLockMode) {
        this.autoPauseEnabled = autoPauseEnabled;
        this.weekLockModeEnabled = weekLockMode;
    }

    @Column(name = "auto_pause", nullable = false)
    public boolean getAutoPauseEnabled() {
        return autoPauseEnabled;
    }

    public void setAutoPauseEnabled(boolean autoPauseEnabled) {
        this.autoPauseEnabled = autoPauseEnabled;
    }

    @Column(name = "week_lock", nullable = false)
    public boolean getWeekLockModeEnabled() {
        return weekLockModeEnabled;
    }

    public void setWeekLockModeEnabled(boolean weekLockMode) {
        this.weekLockModeEnabled = weekLockMode;
    }

     @Column(name = "day_lock", nullable = false)
    public boolean getDayLockModeEnabled() {
        return dayLockModeEnabled;
    }

    public void setDayLockModeEnabled(boolean dayLockModeEnabled) {
        this.dayLockModeEnabled = dayLockModeEnabled;
    }

     @Column(name = "auto_pause_time", nullable = false)
    public double getAutoPauseDuration() {
        return autoPauseDuration;
    }

    public void setAutoPauseDuration(double autoPauseDuration) {
        this.autoPauseDuration = autoPauseDuration;
    }

     @Column(name = "residual_vacation", nullable = false)
    public double getResidualVacation() {
        return residualVacation;
    }

    public void setResidualVacation(double residualVacation) {
        this.residualVacation = residualVacation;
    }

}
