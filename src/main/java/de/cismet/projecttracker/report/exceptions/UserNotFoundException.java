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
public class UserNotFoundException extends Exception {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UserNotFoundException object.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Creates a new UserNotFoundException object.
     *
     * @param  message  DOCUMENT ME!
     */
    public UserNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates a new UserNotFoundException object.
     *
     * @param  cause  DOCUMENT ME!
     */
    public UserNotFoundException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new UserNotFoundException object.
     *
     * @param  message  DOCUMENT ME!
     * @param  cause    DOCUMENT ME!
     */
    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
