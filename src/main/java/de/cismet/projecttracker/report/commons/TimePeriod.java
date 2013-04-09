/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.commons;

import java.util.GregorianCalendar;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class TimePeriod {

    //~ Instance fields --------------------------------------------------------

    private GregorianCalendar start;
    private GregorianCalendar end;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the start
     */
    public GregorianCalendar getStart() {
        return start;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  start  the start to set
     */
    public void setStart(final GregorianCalendar start) {
        this.start = start;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the end
     */
    public GregorianCalendar getEnd() {
        return end;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  end  the end to set
     */
    public void setEnd(final GregorianCalendar end) {
        this.end = end;
    }
}
