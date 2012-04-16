/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.commons.holidayconfig;

/**
 *
 * @author therter
 */
public class HolidayConfItem {
    private String name;
    private boolean halfHoliday;
    private HolidayTime time;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the halfHoliday
     */
    public boolean isHalfHoliday() {
        return halfHoliday;
    }

    /**
     * @param halfHoliday the halfHoliday to set
     */
    public void setHalfHoliday(boolean halfHoliday) {
        this.halfHoliday = halfHoliday;
    }

    /**
     * @return the time
     */
    public HolidayTime getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(HolidayTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return name + " " + halfHoliday + " " + time;
    }

    public boolean isValid() {
        return name != null && time != null && time.isValid();
    }
}
