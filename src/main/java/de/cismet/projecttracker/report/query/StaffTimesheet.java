package de.cismet.projecttracker.report.query;

import de.cismet.projecttracker.report.helper.CalendarHelper;
import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.db.entities.Contract;
import de.cismet.projecttracker.report.db.entities.CostCategory;
import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.db.entities.Staff;
import de.cismet.projecttracker.report.db.entities.WorkPackage;
import de.cismet.projecttracker.report.commons.HolidayEvaluator;
import de.cismet.projecttracker.report.commons.KeyConstants;
import de.cismet.projecttracker.report.exceptions.DataRetrievalException;
import de.cismet.projecttracker.report.exceptions.InvalidDataException;
import java.util.*;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * An object of this class can collect all data, which are required to determine the project activities
 * of the given user in the given time period.
 * Such an object can also evaluate whether all required data are contained in the database.
 * @author therter
 */
public class StaffTimesheet {
    private static final String CONTRACT_QUERY = "select distinct contract from Contract as contract where contract.staff = %d " +
                                                 "and (contract.todate=null or contract.todate>='%s') and contract.fromdate<='%s'";
    private static final String ACTIVITY_QUERY = "select activity from Activity as activity where " +
                                                 "activity.staff = %d and activity.day >='%s' and activity.day<='%s' order by activity.day asc" ;
    private static final String ALL_ACTIVITIES_QUERY = "select activity from Activity as activity where " +
                                                 "activity.staff = %d and activity.day >='%s' and activity.day<='%s' order by activity.day asc" ;
    private static final int WORK_ACTIVITY = 0;
    private static final int START_ACTIVITY = 1;
    private static final int END_ACTIVITY = 2;
    private static Logger logger = Logger.getLogger(StaffTimesheet.class);
    private Hashtable<String, StaffDay> times = new Hashtable<String, StaffDay>();
    private long userId;
    private ResourceBundle config;
    private Session dbManager;
    private double hoursOfWeek;
    private String userName;
    private HolidayEvaluator holidays = new HolidayEvaluator();

    
    public StaffTimesheet(long userId, Session dbManager) throws DataRetrievalException  {
        this.userId = userId;
        this.config = ResourceBundle.getBundle("de.cismet.projecttracker.report.commons.ReportAPIConfig");
        this.dbManager = dbManager;
    }

    /**
     * Checks whether all data for the given time period are available in the database.
     * @param start the first day of the check
     * @param end  the last day of the check
     * @return an Array, that contains all errors, which are occured during the check.
     *          All of these errors are caused by insufficient data within the corresponding database
     * @throws DataRetrievalException if the required data cannot be read from the database for some reason
     */
    public String[] checkData(GregorianCalendar start, GregorianCalendar end) throws DataRetrievalException {
        Vector<String> errors = new Vector<String>();
        try {
            //checks the working hours per week
            try {
                setHoursOfWeek(start, end);
            } catch (InvalidDataException e) {
                errors.add(e.getMessage());
            }
            //checks the user name
            try {
                setUserName();
            } catch (InvalidDataException e) {
                errors.add(e.getMessage());
            }
            
            collectData(start, start, new ArrayList<Activity>());
            
            GregorianCalendar curDay = (GregorianCalendar)start.clone();
            
            while (CalendarHelper.compareDay(curDay, end) <= 0) {
                StaffDay day = times.get( CalendarHelper.toDateString(curDay) );
                if (day != null) {
                    List<String> tmpErrors = day.getErrors();

                    if (errors != null) {
                        errors.addAll(tmpErrors);
                    }
                } else {
                    if (isWorkingDay(curDay)) {
                        //TODO: internationalisieren
                        errors.add(CalendarHelper.toDateString(curDay) + ": Es existieren keine Einträge.");
                    }
                }
                
                curDay.add(GregorianCalendar.DATE, 1);
            }
            
//            // checks whether all required activities are contained in the corresponding database
//            List<Activity> activities = getAllActivities(start, end);
//
//            if (activities != null) {
//                logger.debug(activities.size() + " activities found");
//                GregorianCalendar day = (GregorianCalendar)start.clone();
//
//                if (!isWorkingDay(day)) {
//                    setToNextWorkingDay(day);
//                }
//
//                for (Activity tmp : activities) {
//                    GregorianCalendar activityDay = new GregorianCalendar();
//                    activityDay.setTimeInMillis(tmp.getDay().getTime());
//                    int comparison = CalendarHelper.compareDay(day, activityDay);
//
//                    if ( comparison == 0) {
//                        //day == activityDay
//                        setToNextWorkingDay(day);
//                    } else if (comparison < 0) {
//                        //day < activityDay
//                        do {
//                            if (!isWorkingDay(day)) {
//                                errors.add( CalendarHelper.dateFormatter.format(day.getTime()) + ": " + config.getString(KeyConstants.NO_ACTIVITY_FOUND_KEY));
//                            }
//                            day.add(GregorianCalendar.DATE, 1);
//                        } while(CalendarHelper.compareDay(day, activityDay) < 0 && CalendarHelper.compareDay(day, end) <= 0);
//                    } else {
//                        //day > activityDay
//
//                    }
//                }
//                // add all days from the day of the last activity to the end date
//                while(CalendarHelper.compareDay(day, end) <= 0) {
//                    errors.add( CalendarHelper.dateFormatter.format(day.getTime()) + ": " + config.getString(KeyConstants.NO_ACTIVITY_FOUND_KEY));
//                    setToNextWorkingDay(day);
//                }
//            }
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }

        return errors.toArray(new String[errors.size()]);
    }

    /**
     * Set the given calendar object to the next working day.
     *
     * @param day
     */
    private void setToNextWorkingDay(GregorianCalendar day) {
        do {
            day.add(GregorianCalendar.DATE, 1);
        } while ( !isWorkingDay(day) );
    }


    /**
     * Checks if the given calendar object represents a working day.
     * This means a day, that is no weekend and no holiday. At the moment, half holiday will be
     * ignored within this method.
     * @param day
     * @return
     */
    private boolean isWorkingDay(GregorianCalendar day) {
        return ( day.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY  &&
                 day.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY &&
                 holidays.isHoliday(day) == HolidayEvaluator.WORKDAY);
    }


    /**
     * collects all activity data for the given time period
     *
     * @param firstDay the first day, the data should be collected for
     * @param lastDay the last day, the data should be collected for
     * @throws DataRetrievalException
     */
    public Map<String, StaffDay> collectData(GregorianCalendar firstDay, GregorianCalendar lastDay, List<Activity> usedActivities) throws DataRetrievalException {
        try {
            times.clear();
            setHoursOfWeek(firstDay, lastDay);
            setUserName();
            List<Activity> activities = getActivities(firstDay, lastDay);


            if (activities != null) {
                logger.debug(activities.size() + " activities found");
                for (Activity tmp : activities) {
                    StaffDay timesForDay = times.get( CalendarHelper.toDateString(tmp.getDay()) );
                    
                    //check, whether any activity for this day was already processed
                    if (timesForDay == null) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("day not found for  " + CalendarHelper.dateFormatter.format(tmp.getDay()) + " " + CalendarHelper.toDateString(tmp.getDay()) + ". Create new Day" );
                        }
                        timesForDay = new StaffDay(tmp.getDay());
                        times.put( CalendarHelper.toDateString(tmp.getDay()), timesForDay );
                    } else {
                        logger.debug("day found");
                    }

                    if (tmp.getKindofactivity() == WORK_ACTIVITY) {
                        Project project = tmp.getWorkPackage().getProject();

                        ProjectActivity projectActivity = (ProjectActivity)timesForDay.getProjectActivity(project);

                        if ( projectActivity == null ) {
                            projectActivity = new ProjectActivity(project);
                            timesForDay.addProjectActivity(project, projectActivity);
                        }

                        projectActivity.addHours((tmp.getWorkPackage() != null ? tmp.getWorkPackage().getCostCategory() : null), tmp.getWorkPackage(), tmp.getWorkinghours());
                    } else if (tmp.getKindofactivity() == START_ACTIVITY) {
                        timesForDay.setStart(tmp.getDay());
                    } else if (tmp.getKindofactivity() == END_ACTIVITY) {
                        timesForDay.setEnd(tmp.getDay());
                    }
                    usedActivities.add(tmp);
                }
            }
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }
        
        return times;
    }


    /**
     * reads all activity of the given user for the given time period from the database,
     * which are not only for internal use
     * @param start
     * @param end
     * @return the corresponding activities
     * @throws DataRetrievalException
     */
    private List<Activity> getActivities(GregorianCalendar start, GregorianCalendar end) throws DataRetrievalException {
        try {
            String startDay = CalendarHelper.dateFormatter.format( start.getTime() );
            String endDay = CalendarHelper.dateFormatter.format( end.getTime() );
            Query query = dbManager.createQuery( String.format(ACTIVITY_QUERY, userId, startDay, endDay) );
            List<Activity> activities = query.list();

            logger.debug(activities.size() + " activities found");
            return activities;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }
    }

    
    /**
     * reads all activity of the given user for the given time period from the database
     * @param start
     * @param end
     * @return the corresponding activities
     * @throws DataRetrievalException
     */
    public List<Activity> getAllActivities(GregorianCalendar start, GregorianCalendar end, long userId) throws DataRetrievalException {
        try {
            String startDay = CalendarHelper.dateFormatter.format( start.getTime() );
            String endDay = CalendarHelper.dateFormatter.format( end.getTime() );
            Query query = dbManager.createQuery( String.format(ALL_ACTIVITIES_QUERY, userId, startDay, endDay) );
            List<Activity> activities = query.list();

            logger.debug(activities.size() + " activities found");
            return activities;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }
    }

    /**
     * set the <code>hoursOfWeek</code> variable
     * @throws DataRetrievalException
     * @throws InvalidDataException
     */
    private void setHoursOfWeek(GregorianCalendar start, GregorianCalendar end)  throws DataRetrievalException, InvalidDataException {
        try {
            String startDate = CalendarHelper.dateFormatter.format( start.getTime() );
            String endDate = CalendarHelper.dateFormatter.format( end.getTime() );
            Query query = dbManager.createQuery( String.format(CONTRACT_QUERY, userId, endDate, startDate) );
            List<Contract> contracts = query.list();

            if ( contracts.size() < 1 ) {
                throw new InvalidDataException( config.getString(KeyConstants.NO_VALID_CONTRACT_FOUND_KEY) );
            } else if ( contracts.size() > 1 ) {
                throw new InvalidDataException( config.getString(KeyConstants.TOO_MANY_CONTRACTS_FOUND_KEY) );
            }
            setHoursOfWeek(contracts.get(0).getWhow());
            logger.debug("weekly hours of work: " + getHoursOfWeek());
        } catch (InvalidDataException e) {
            throw e;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }
    }

    /**
     * set the variable <code>userName</code>
     * @throws DataRetrievalException
     * @throws InvalidDataException
     */
    private void setUserName() throws DataRetrievalException, InvalidDataException {
        try {
            Staff staff = (Staff)dbManager.createCriteria(Staff.class).add(Restrictions.eq("id", userId)).uniqueResult();

            logger.error("staff: " + staff);
            if (staff == null) {
                logger.error("No user with the id " + userId + " was found.");
                throw new InvalidDataException( "No user with the id " + userId + " was found." );
            }
            userName = staff.getFirstname() + " " + staff.getName();
        } catch (InvalidDataException e) {
            throw e;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }
    }


    /**
     * @param projectId
     * @param date
     * @return the hours within the given eu project on the given day, which are not contained in
     * the categories demonstration, research and developement or managament
     */
    public ProjectActivity getProjectActivityForDay(Project project, Date date) {
        StaffDay day = times.get( CalendarHelper.toDateString(date) );

        if (day != null) {
            ProjectActivity activity = day.getProjectActivity(project);

            return activity;
        }

        return null;
    }


    /**
     *
     * @param project
     * @param category if category == null, all work packages, which was billed on the given project will be returned.
     *                 Otherwise, only the work packages, which are billed on the given category will be returned.
     * @param start
     * @param end
     * @return
     */
    public WorkPackage[] getBilledWorkPackagesForProject(Project project, CostCategory category, GregorianCalendar start, GregorianCalendar end) {
        GregorianCalendar tmp = (GregorianCalendar)start.clone();
        List<WorkPackage> result = new ArrayList<WorkPackage>();

        while ( CalendarHelper.isDateLessOrEqual(tmp, end) ) {
            StaffDay day = times.get( CalendarHelper.toDateString(tmp.getTime()) );
            if (day != null) {
                ProjectActivity activity = day.getProjectActivity( project );
                if (activity != null) {
                    List<WorkPackage> wp;

                    if (category == null) {
                        wp = activity.getWorkPackages();
                    } else {
                        wp = activity.getWorkPackagesForCostCategory(category);
                    }

                    if (wp != null) {
                        for (WorkPackage tmpWp : wp) {
                            if (!result.contains(tmpWp)) {
                                result.add(tmpWp);
                            }
                        }
                    }
                }
            }
            tmp.add(GregorianCalendar.DATE, 1);
        }
        return result.toArray( new WorkPackage[result.size()] );
    }



    /**
     * @param projectId
     * @param date
     * @return the hours within the given internal project for the given day
     */
    public double getHoursOfWorkForDay(Date date) {
        StaffDay day = times.get( CalendarHelper.toDateString(date) );
        double result = 0.0;

        if (day != null) {
            List<ProjectActivity> activities = day.getAllProjectActivities();

            for (ProjectActivity tmp : activities) {
                result += tmp.getHoursOfWork();
            }
        }

        return result;
    }

    /**
     * @param date
     * @return the hours of annual leave for the given day
     */
    public double getAnnualLeave(Date date) {
        StaffDay day = times.get( CalendarHelper.toDateString(date) );
        if (day != null) {
            return day.getAnnualLeave();
        }
        return 0.0;
    }

    /**
     * @param date
     * @return the hours of special leave for the given day
     */
    public double getSpecialLeave(Date date) {
        StaffDay day = times.get( CalendarHelper.toDateString(date) );
        if (day != null) {
            return day.getSpecialLeave();
        }
        return 0.0;
    }

    /**
     * @param date
     * @return the hours of illness for the given day
     */
    public double getIllness(Date date) {
        StaffDay day = times.get( CalendarHelper.toDateString(date) );
        if (day != null) {
            return day.getIllness();
        }
        return 0.0;
    }

    /**
     * @param date
     * @return the training hours for the given day
     */
    public double getTraining(Date date) {
        StaffDay day = times.get( CalendarHelper.toDateString(date) );
        if (day != null) {
            return day.getTraining();
        }
        return 0.0;
    }


    /**
     * @return the hoursOfWeek
     */
    public double getHoursOfWeek() {
        return hoursOfWeek;
    }

    /**
     * @param hoursOfWeek the hoursOfWeek to set
     */
    public void setHoursOfWeek(double hoursOfWeek) {
        this.hoursOfWeek = hoursOfWeek;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
}
