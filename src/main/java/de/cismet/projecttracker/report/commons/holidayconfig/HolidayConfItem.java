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
package de.cismet.projecttracker.report.commons.holidayconfig;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class HolidayConfItem {

    //~ Instance fields --------------------------------------------------------

    private String name;
    private boolean halfHoliday;
    private HolidayTime time;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the name
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  name  the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the halfHoliday
     */
    public boolean isHalfHoliday() {
        return halfHoliday;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  halfHoliday  the halfHoliday to set
     */
    public void setHalfHoliday(final boolean halfHoliday) {
        this.halfHoliday = halfHoliday;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the time
     */
    public HolidayTime getTime() {
        return time;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  time  the time to set
     */
    public void setTime(final HolidayTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return name + " " + halfHoliday + " " + time;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isValid() {
        return (name != null) && (time != null) && time.isValid();
    }
}
