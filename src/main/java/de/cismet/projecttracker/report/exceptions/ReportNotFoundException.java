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
public class ReportNotFoundException extends Exception {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ReportNotFoundException object.
     */
    public ReportNotFoundException() {
        super();
    }

    /**
     * Creates a new ReportNotFoundException object.
     *
     * @param  message  DOCUMENT ME!
     */
    public ReportNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates a new ReportNotFoundException object.
     *
     * @param  cause  DOCUMENT ME!
     */
    public ReportNotFoundException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new ReportNotFoundException object.
     *
     * @param  message  DOCUMENT ME!
     * @param  cause    DOCUMENT ME!
     */
    public ReportNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
