/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.exceptions;

/**
 *
 * @author therter
 */
public class ReportNotFoundException extends Exception {

    public ReportNotFoundException() {
        super();
    }

    public ReportNotFoundException(String message) {
        super(message);
    }

    public ReportNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
