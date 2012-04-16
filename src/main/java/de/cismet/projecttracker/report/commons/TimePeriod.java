package de.cismet.projecttracker.report.commons;

import java.util.GregorianCalendar;

/**
 *
 * @author therter
 */
public class TimePeriod {
    private GregorianCalendar start;
    private GregorianCalendar end;

    /**
     * @return the start
     */
    public GregorianCalendar getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(GregorianCalendar start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public GregorianCalendar getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(GregorianCalendar end) {
        this.end = end;
    }
}
