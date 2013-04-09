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
<<<<<<< HEAD

=======
>>>>>>> 57548bb7f3dbae91d1afebfec33bd4b494d16e66
package de.cismet.projecttracker.report.commons.holidayconfig;

import java.util.GregorianCalendar;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class HolidayTime {

    //~ Instance fields --------------------------------------------------------

    private int daysAfterEaster;
    private GregorianCalendar fixDate;
    private boolean everyYear;
    private boolean valid = false;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the daysAfterEaster
     */
    public int getDaysAfterEaster() {
        return daysAfterEaster;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  daysAfterEaster  the daysAfterEaster to set
     */
    public void setDaysAfterEaster(final int daysAfterEaster) {
        this.daysAfterEaster = daysAfterEaster;
        this.valid = true;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the fixDate
     */
    public GregorianCalendar getFixDate() {
        return fixDate;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fixDate  the fixDate to set
     */
    public void setFixDate(final GregorianCalendar fixDate) {
        this.fixDate = fixDate;
        this.valid = true;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the everyYear
     */
    public boolean isEveryYear() {
        return everyYear;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  everyYear  the everyYear to set
     */
    public void setEveryYear(final boolean everyYear) {
        this.everyYear = everyYear;
    }

    @Override
    public String toString() {
        if (fixDate != null) {
            return everyYear + " " + fixDate.get(GregorianCalendar.YEAR) + "-" + fixDate.get(GregorianCalendar.MONTH)
                        + "-" + fixDate.get(GregorianCalendar.DAY_OF_MONTH);
        } else {
            return "" + daysAfterEaster;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isValid() {
        return valid;
    }
}
