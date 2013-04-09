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
package de.cismet.projecttracker.report.timetracker;

import org.apache.log4j.Logger;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import de.cismet.projecttracker.report.db.entities.Staff;
import de.cismet.projecttracker.report.exceptions.UserNotFoundException;

import de.cismet.web.timetracker.Database;
import de.cismet.web.timetracker.Query;
import de.cismet.web.timetracker.TimeTrackerConstants;
import de.cismet.web.timetracker.types.HoursOfWork;
import de.cismet.web.timetracker.types.TimesheetEntry;
import de.cismet.web.timetracker.types.TimesheetSet;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class TimetrackerQuery {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger logger = Logger.getLogger(TimetrackerQuery.class);

    //~ Instance fields --------------------------------------------------------

    private ResourceBundle config;
    private Database db;
    private Query query;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TimetrackerQuery object.
     */
    public TimetrackerQuery() {
        try {
            this.config = ResourceBundle.getBundle("de.cismet.projecttracker.report.commons.ReportAPIConfig");
            final ResourceBundle bundle = ResourceBundle.getBundle(
                    "de.cismet.projecttracker.report.timetracker.timetracker");
            final String drivername = bundle.getString("driver");
            final String url = bundle.getString("url");
            final String user = bundle.getString("user");
            final String pwd = bundle.getString("pwd");
            db = new Database(drivername, url, user, pwd);
            query = new Query(db);
        } catch (final MissingResourceException e) {
            logger.error("Cannot read timetracker configuration.", e);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   user  DOCUMENT ME!
     * @param   day   DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  UserNotFoundException  DOCUMENT ME!
     */
    public HoursOfWork getHoursOfWork(final Staff user, final GregorianCalendar day) throws UserNotFoundException {
        try {
            final int uid = db.getIdByName(user.getFirstname(), user.getName());

            if (uid == -1) {
                throw new UserNotFoundException(config.getString("timetrackerUserNotFound"));
            }
            final HoursOfWork how = new HoursOfWork(db.hasAutopause(uid));
            query.getHoursOfWork(uid, day, how);

            return how;
        } catch (final Exception e) {
            if (e instanceof UserNotFoundException) {
                throw (UserNotFoundException)e;
            }
            logger.error("Error while trying to calculate the hours of work.", e);
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   user  DOCUMENT ME!
     * @param   day   DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  UserNotFoundException  DOCUMENT ME!
     */
    public GregorianCalendar getStartOfWork(final Staff user, final GregorianCalendar day)
            throws UserNotFoundException {
        try {
            final int uid = db.getIdByName(user.getFirstname(), user.getName());
            GregorianCalendar start = (GregorianCalendar)day.clone();
            start.add(GregorianCalendar.DATE, 1);

            if (uid == -1) {
                throw new UserNotFoundException(config.getString("timetrackerUserNotFound"));
            }

            final List<TimesheetSet> times = db.getTimeOfWork(uid, day, 1);

            if ((times != null) && !times.isEmpty()) {
                final TimesheetSet tmp = times.get(0);
                TimesheetEntry entry = null;

                while ((entry = tmp.next()) != null) {
                    if ((entry.getAction() == TimeTrackerConstants.COME) && entry.getTime().before(start)) {
                        start = entry.getTime();
                    }
                }
            }

            if (start.get(GregorianCalendar.DATE) == day.get(GregorianCalendar.DATE)) {
                return start;
            } else {
                return null;
            }
        } catch (final Exception e) {
            if (e instanceof UserNotFoundException) {
                throw (UserNotFoundException)e;
            }
            logger.error("Error while trying to calculate start of work.", e);
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   user  DOCUMENT ME!
     * @param   day   DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  UserNotFoundException  DOCUMENT ME!
     */
    public GregorianCalendar getEndOfWork(final Staff user, final GregorianCalendar day) throws UserNotFoundException {
        try {
            final int uid = db.getIdByName(user.getFirstname(), user.getName());
            GregorianCalendar end = (GregorianCalendar)day.clone();
            end.add(GregorianCalendar.DATE, -1);

            if (uid == -1) {
                throw new UserNotFoundException(config.getString("timetrackerUserNotFound"));
            }

            final List<TimesheetSet> times = db.getTimeOfWork(uid, day, 1);

            if ((times != null) && !times.isEmpty()) {
                final TimesheetSet tmp = times.get(0);
                TimesheetEntry entry = null;

                while ((entry = tmp.next()) != null) {
                    if ((entry.getAction() == TimeTrackerConstants.GO) && entry.getTime().after(end)) {
                        end = entry.getTime();
                    }
                }
            }

            if (end.get(GregorianCalendar.DATE) == day.get(GregorianCalendar.DATE)) {
                return end;
            } else {
                return null;
            }
        } catch (final Exception e) {
            if (e instanceof UserNotFoundException) {
                throw (UserNotFoundException)e;
            }
            logger.error("Error while trying to calculate start of work.", e);
        }

        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        if (db != null) {
            db.close();
        }
    }
}
