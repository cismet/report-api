package de.cismet.projecttracker.report.query;

import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.db.entities.WorkCategory;
import de.cismet.projecttracker.report.db.entities.WorkPackage;
import de.cismet.projecttracker.report.exceptions.DataRetrievalException;
import de.cismet.projecttracker.report.helper.CalendarHelper;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author therter
 */
public class ProjectTimesheet {
    private static Logger logger = Logger.getLogger(ProjectTimesheet.class);
    private PreparedDBManager dbManager = new PreparedDBManager();
    private Project project;
    private Hashtable<String, ProjectDay> times = new Hashtable<String, ProjectDay>();
    

    public ProjectTimesheet(PreparedDBManager dbManager, Project project) {
        this.dbManager = dbManager;
        this.project = project;
    }

//    /**
//     * Checks whether all data for the given time period are available in the database.
//     * @param start the first day of the check
//     * @param end  the last day of the check
//     * @return an Array, that contains all errors, which are occured during the check.
//     *          All of these errors are caused by insufficient data within the corresponding database
//     * @throws DataRetrievalException if the required data cannot be read from the database for some reason
//     */
//    public String[] checkData(GregorianCalendar start, GregorianCalendar end) throws DataRetrievalException {
//        Vector<String> errors = new Vector<String>();
//
//        try {
//            List<Staff> staffs = dbManager.getAllObjects(Staff.class);
//            for (Staff staff : staffs) {
//                // checks whether all required activities are contained in the corresponding database
//                List<Activity> activities = dbManager.getActivities(start, end, staff, project, PreparedDBManager.ActivityDeclaration.ALL);
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
     * collects all activity data for the given time period
     *
     * @param firstDay the first day, the data should be collected for
     * @param lastDay the last day, the data should be collected for
     * @throws DataRetrievalException
     */
    public void collectData(GregorianCalendar firstDay, GregorianCalendar lastDay, List<Activity> usedActivities) throws DataRetrievalException {
        try {
            List<Activity> activities = dbManager.getActivities(firstDay, lastDay, null, project);

            if (activities != null) {
                logger.debug(activities.size() + " activities found");
                for (Activity tmp : activities) {
                    ProjectDay timesForDay = times.get( CalendarHelper.toDateString(tmp.getDay()) );
                    //check, whether any activity for this day was already processed
                    if (timesForDay == null) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("day not found for  " + CalendarHelper.toDateString(tmp.getDay()) + " " + CalendarHelper.toDateString(tmp.getDay()) + ". Create new Day" );
                        }
                        timesForDay = new ProjectDay();
                        times.put( CalendarHelper.toDateString(tmp.getDay()), timesForDay );
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
     *
     * @param start
     * @param end
     * @param wp if wp is null, the working hours of all work packages will be returned
     * @param declaration determines, if the internal flag of the activity should be considered
     * @param wc if work category is null, the working hours of all work categories will be returned
     * @return the working hours of the project within the given time period and with the given attributes
     */
    public double getWorkingHours(GregorianCalendar start, GregorianCalendar end, WorkPackage wp, WorkCategory wc) {
        GregorianCalendar tmp = (GregorianCalendar)start.clone();
        double hours = 0.0;

        while ( CalendarHelper.isDateLessOrEqual(tmp, end) ) {
            ProjectDay day = times.get( CalendarHelper.toDateString(tmp) );
            if (day != null) {
                hours += day.getHoursOfWork(wp, wc);
            }
            tmp.add(GregorianCalendar.DATE, 1);
        }

        return hours;
    }
}
