/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.projecttracker.report.db.entities;

import javax.persistence.*;

/**
 * DOCUMENT ME!
 *
 * @author   dmeiers
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "profile",
    schema = "public"
)
public class Profile extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private boolean autoPauseEnabled;
    private boolean weekLockModeEnabled;
    private boolean dayLockModeEnabled;
    private double autoPauseDuration;
    private double residualVacation;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Profile object.
     */
    public Profile() {
    }

    /**
     * Creates a new Profile object.
     *
     * @param  autoPauseEnabled  DOCUMENT ME!
     * @param  weekLockMode      DOCUMENT ME!
     */
    public Profile(final boolean autoPauseEnabled, final boolean weekLockMode) {
        this.autoPauseEnabled = autoPauseEnabled;
        this.weekLockModeEnabled = weekLockMode;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "auto_pause",
        nullable = false
    )
    public boolean getAutoPauseEnabled() {
        return autoPauseEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  autoPauseEnabled  DOCUMENT ME!
     */
    public void setAutoPauseEnabled(final boolean autoPauseEnabled) {
        this.autoPauseEnabled = autoPauseEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "week_lock",
        nullable = false
    )
    public boolean getWeekLockModeEnabled() {
        return weekLockModeEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  weekLockMode  DOCUMENT ME!
     */
    public void setWeekLockModeEnabled(final boolean weekLockMode) {
        this.weekLockModeEnabled = weekLockMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "day_lock",
        nullable = false
    )
    public boolean getDayLockModeEnabled() {
        return dayLockModeEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dayLockModeEnabled  DOCUMENT ME!
     */
    public void setDayLockModeEnabled(final boolean dayLockModeEnabled) {
        this.dayLockModeEnabled = dayLockModeEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "auto_pause_time",
        nullable = false
    )
    public double getAutoPauseDuration() {
        return autoPauseDuration;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  autoPauseDuration  DOCUMENT ME!
     */
    public void setAutoPauseDuration(final double autoPauseDuration) {
        this.autoPauseDuration = autoPauseDuration;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "residual_vacation",
        nullable = false
    )
    public double getResidualVacation() {
        return residualVacation;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  residualVacation  DOCUMENT ME!
     */
    public void setResidualVacation(final double residualVacation) {
        this.residualVacation = residualVacation;
    }
}
