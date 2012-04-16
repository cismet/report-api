/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.exceptions;

/**
 *
 * @author therter
 */
public class InvalidDataException extends Exception {

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(Throwable cause) {
        super(cause);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
