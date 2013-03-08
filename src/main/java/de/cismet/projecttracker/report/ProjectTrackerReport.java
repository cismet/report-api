/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report;

import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.exceptions.DataRetrievalException;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author therter
 */
public abstract class ProjectTrackerReport {
    public abstract void init();
    public boolean supportUserSpecificreportGeneration() {
        return true;
    }

    public boolean supportUserUnspecificreportGeneration() {
        return true;
    }
    
    public abstract String checkForReport(GregorianCalendar start, GregorianCalendar end) throws DataRetrievalException;
    public abstract String checkForReport(GregorianCalendar start, GregorianCalendar end, long userID) throws DataRetrievalException;
    public abstract byte[] generateReport(GregorianCalendar start, GregorianCalendar end, List<Activity> usedActivities) throws DataRetrievalException;
    public abstract byte[] generateReport(GregorianCalendar start, GregorianCalendar end, long userID, List<Activity> usedActivities) throws DataRetrievalException;
    public abstract String getMIMEType();
    public abstract String getFileExtension();
    public abstract void destroy();
    public abstract boolean canHandle(byte[] doc);
    public abstract String getReportName();
}
