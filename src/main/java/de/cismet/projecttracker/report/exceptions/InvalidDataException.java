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
public class InvalidDataException extends Exception {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new InvalidDataException object.
     */
    public InvalidDataException() {
        super();
    }

    /**
     * Creates a new InvalidDataException object.
     *
     * @param  message  DOCUMENT ME!
     */
    public InvalidDataException(final String message) {
        super(message);
    }

    /**
     * Creates a new InvalidDataException object.
     *
     * @param  cause  DOCUMENT ME!
     */
    public InvalidDataException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new InvalidDataException object.
     *
     * @param  message  DOCUMENT ME!
     * @param  cause    DOCUMENT ME!
     */
    public InvalidDataException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
