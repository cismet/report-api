/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.commons.holidayconfig;

import java.util.GregorianCalendar;

/**
 *
 * @author therter
 */
public class HolidayTime {
    private int daysAfterEaster;
    private GregorianCalendar fixDate;
    private boolean everyYear;
    private boolean valid = false;

    /**
     * @return the daysAfterEaster
     */
    public int getDaysAfterEaster() {
        return daysAfterEaster;
    }

    /**
     * @param daysAfterEaster the daysAfterEaster to set
     */
    public void setDaysAfterEaster(int daysAfterEaster) {
        this.daysAfterEaster = daysAfterEaster;
        this.valid = true;
    }

    /**
     * @return the fixDate
     */
    public GregorianCalendar getFixDate() {
        return fixDate;
    }

    /**
     * @param fixDate the fixDate to set
     */
    public void setFixDate(GregorianCalendar fixDate) {
        this.fixDate = fixDate;
        this.valid = true;
    }

    /**
     * @return the everyYear
     */
    public boolean isEveryYear() {
        return everyYear;
    }

    /**
     * @param everyYear the everyYear to set
     */
    public void setEveryYear(boolean everyYear) {
        this.everyYear = everyYear;
    }

    @Override
    public String toString() {
        if (fixDate != null) {
            return everyYear + " " + fixDate.get( GregorianCalendar.YEAR ) + "-" + fixDate.get( GregorianCalendar.MONTH ) + "-" + fixDate.get( GregorianCalendar.DAY_OF_MONTH );
        } else {
            return "" + daysAfterEaster;
        }
    }


    public boolean isValid() {
        return valid;
    }
}
