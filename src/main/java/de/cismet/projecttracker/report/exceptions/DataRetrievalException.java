/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.exceptions;

/**
 *
 * @author therter
 */
public class DataRetrievalException extends Exception {

    public DataRetrievalException() {
        super();
    }

    public DataRetrievalException(String message) {
        super(message);
    }

    public DataRetrievalException(Throwable cause) {
        super(cause);
    }

    public DataRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
