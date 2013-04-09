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
package de.cismet.projecttracker.report.exceptions;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ReportNotFoundException1 extends Exception {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ReportNotFoundException1 object.
     */
    public ReportNotFoundException1() {
        super();
    }

    /**
     * Creates a new ReportNotFoundException1 object.
     *
     * @param  message  DOCUMENT ME!
     */
    public ReportNotFoundException1(final String message) {
        super(message);
    }

    /**
     * Creates a new ReportNotFoundException1 object.
     *
     * @param  cause  DOCUMENT ME!
     */
    public ReportNotFoundException1(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new ReportNotFoundException1 object.
     *
     * @param  message  DOCUMENT ME!
     * @param  cause    DOCUMENT ME!
     */
    public ReportNotFoundException1(final String message, final Throwable cause) {
        super(message, cause);
    }
}
