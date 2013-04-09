/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.query;

import org.apache.log4j.Logger;

import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.db.entities.WorkCategory;
import de.cismet.projecttracker.report.db.entities.WorkPackage;
import de.cismet.projecttracker.report.exceptions.DataRetrievalException;
import de.cismet.projecttracker.report.helper.CalendarHelper;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ProjectTimesheet {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger(ProjectTimesheet.class);

    //~ Instance fields --------------------------------------------------------

    private PreparedDBManager dbManager = new PreparedDBManager();
    private Project project;
    private Hashtable<String, ProjectDay> times = new Hashtable<String, ProjectDay>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ProjectTimesheet object.
     *
     * @param  dbManager  DOCUMENT ME!
     * @param  project    DOCUMENT ME!
     */
    public ProjectTimesheet(final PreparedDBManager dbManager, final Project project) {
        this.dbManager = dbManager;
        this.project = project;
    }

    //~ Methods ----------------------------------------------------------------

// /**
// * Checks whether all data for the given time period are available in the database.
// * @param start the first day of the check
// * @param end  the last day of the check
// * @return an Array, that contains all errors, which are occured during the check.
// *          All of these errors are caused by insufficient data within the corresponding database
// * @throws DataRetrievalException if the required data cannot be read from the database for some reason
// */
// public String[] checkData(GregorianCalendar start, GregorianCalendar end) throws DataRetrievalException {
// Vector<String> errors = new Vector<String>();
//
// try {
// List<Staff> staffs = dbManager.getAllObjects(Staff.class);
// for (Staff staff : staffs) {
// // checks whether all required activities are contained in the corresponding database
// List<Activity> activities = dbManager.getActivities(start, end, staff, project, PreparedDBManager.ActivityDeclaration.ALL);
//
//                if (activities != null) {
//                    logger.debug(activities.size() + " activities found");
//                    GregorianCalendar day = (GregorianCalendar)start.clone();
//
//                    if (!CalendarHelper.isWorkingDay(day)) {
//                        CalendarHelper.setToNextWorkingDay(day);
//                    }
//
//                    for (Activity tmp : activities) {
//                        GregorianCalendar activityDay = new GregorianCalendar();
//                        activityDay.setTimeInMillis(tmp.getDay().getTime());
//                        int comparison = CalendarHelper.compareDay(day, activityDay);
//
//                        if ( comparison == 0) {
//                            //day == activityDay
//                            CalendarHelper.setToNextWorkingDay(day);
//                        } else if (comparison < 0) {
//                            //day < activityDay
//                            do {
//                                if (!CalendarHelper.isWorkingDay(day)) {
//                                    errors.add( CalendarHelper.dateFormatter.format(day.getTime()) + ": " + config.getString(KeyConstants.NO_ACTIVITY_FOUND_KEY));
//                                }
//                                day.add(GregorianCalendar.DATE, 1);
//                            } while(CalendarHelper.compareDay(day, activityDay) < 0 && CalendarHelper.compareDay(day, end) <= 0);
//                        } else {
//                            //day > activityDay
//
//                        }
//                    }
//                    // add all days from the day of the last activity to the end date
//                    while(CalendarHelper.compareDay(day, end) <= 0) {
//                        errors.add( CalendarHelper.dateFormatter.format(day.getTime()) + ": " + config.getString(KeyConstants.NO_ACTIVITY_FOUND_KEY));
//                        CalendarHelper.setToNextWorkingDay(day);
//                    }
//                }
//            }
//        } catch (Throwable t) {
//            logger.error("Error:", t);
//            throw new DataRetrievalException(t.getMessage(), t);
//        }
//
//        return errors.toArray(new String[errors.size()]);
//    }

    /**
     * collects all activity data for the given time period.
     *
     * @param   firstDay        the first day, the data should be collected for
     * @param   lastDay         the last day, the data should be collected for
     * @param   usedActivities  DOCUMENT ME!
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public void collectData(final GregorianCalendar firstDay,
            final GregorianCalendar lastDay,
            final List<Activity> usedActivities) throws DataRetrievalException {
        try {
            final List<Activity> activities = dbManager.getActivities(firstDay, lastDay, null, project);

            if (activities != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug(activities.size() + " activities found");
                }
                for (final Activity tmp : activities) {
                    ProjectDay timesForDay = times.get(CalendarHelper.toDateString(tmp.getDay()));
                    // check, whether any activity for this day was already processed
                    if (timesForDay == null) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("day not found for  " + CalendarHelper.toDateString(tmp.getDay()) + " "
                                        + CalendarHelper.toDateString(tmp.getDay()) + ". Create new Day");
                        }
                        timesForDay = new ProjectDay();
                        times.put(CalendarHelper.toDateString(tmp.getDay()), timesForDay);
                    }

                    timesForDay.addProjectActivity(tmp);

                    usedActivities.add(tmp);
                }
            }
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   start  DOCUMENT ME!
     * @param   end    DOCUMENT ME!
     * @param   wp     if wp is null, the working hours of all work packages will be returned
     * @param   wc     if work category is null, the working hours of all work categories will be returned
     *
     * @return  the working hours of the project within the given time period and with the given attributes
     */
    public double getWorkingHours(final GregorianCalendar start,
            final GregorianCalendar end,
            final WorkPackage wp,
            final WorkCategory wc) {
        final GregorianCalendar tmp = (GregorianCalendar)start.clone();
        double hours = 0.0;

        while (CalendarHelper.isDateLessOrEqual(tmp, end)) {
            final ProjectDay day = times.get(CalendarHelper.toDateString(tmp));
            if (day != null) {
                hours += day.getHoursOfWork(wp, wc);
            }
            tmp.add(GregorianCalendar.DATE, 1);
        }

        return hours;
    }
}
