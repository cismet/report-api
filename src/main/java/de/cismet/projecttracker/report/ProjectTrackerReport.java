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
package de.cismet.projecttracker.report;

import org.hibernate.Session;

import java.util.GregorianCalendar;
import java.util.List;

import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.exceptions.DataRetrievalException;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public abstract class ProjectTrackerReport {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    public abstract void init();
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean supportUserSpecificreportGeneration() {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean supportUserUnspecificreportGeneration() {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   start  DOCUMENT ME!
     * @param   end    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public abstract String checkForReport(GregorianCalendar start, GregorianCalendar end) throws DataRetrievalException;
    /**
     * DOCUMENT ME!
     *
     * @param   start   DOCUMENT ME!
     * @param   end     DOCUMENT ME!
     * @param   userID  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public abstract String checkForReport(GregorianCalendar start, GregorianCalendar end, long userID)
            throws DataRetrievalException;
    /**
     * DOCUMENT ME!
     *
     * @param   start           DOCUMENT ME!
     * @param   end             DOCUMENT ME!
     * @param   usedActivities  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public abstract byte[] generateReport(GregorianCalendar start, GregorianCalendar end, List<Activity> usedActivities)
            throws DataRetrievalException;
    /**
     * DOCUMENT ME!
     *
     * @param   start           DOCUMENT ME!
     * @param   end             DOCUMENT ME!
     * @param   userID          DOCUMENT ME!
     * @param   usedActivities  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public abstract byte[] generateReport(GregorianCalendar start,
            GregorianCalendar end,
            long userID,
            List<Activity> usedActivities) throws DataRetrievalException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getMIMEType();
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getFileExtension();
    /**
     * DOCUMENT ME!
     */
    public abstract void destroy();
    /**
     * DOCUMENT ME!
     *
     * @param   doc  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract boolean canHandle(byte[] doc);
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getReportName();
}
