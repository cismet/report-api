/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.query;

import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.GregorianCalendar;
import java.util.List;

import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.db.entities.Staff;
import de.cismet.projecttracker.report.exceptions.DataRetrievalException;
import de.cismet.projecttracker.report.helper.CalendarHelper;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class PreparedDBManager extends DBManager {

    //~ Static fields/initializers ---------------------------------------------

    private static final String CONTRACT_QUERY =
        "select distinct contract from Contract as contract where contract.staff = %d "
                + "and (contract.todate=null or contract.todate>='%s') and contract.fromdate<='%s'";
    private static Logger logger = Logger.getLogger(PreparedDBManager.class);

    //~ Methods ----------------------------------------------------------------

    /**
     * reads all activity of the given user for the given time period from the database, which are not only for internal
     * use.
     *
     * @param   start    the start of the time period, the activities should be in
     * @param   end      the end of the time period, the activities should be in
     * @param   staff    the staff, the activities should be from. If the staff is null, the activities of all staff
     *                   will be returned
     * @param   project  the project, the activities should be assigned to or if the project should not be considered
     *
     * @return  the corresponding activities sorted by day in ascending order
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public List<Activity> getActivities(final GregorianCalendar start,
            final GregorianCalendar end,
            final Staff staff,
            final Project project) throws DataRetrievalException {
        try {
            final Criteria crit = hibernateSession.createCriteria(Activity.class)
                        .add(Restrictions.between("day", start.getTime(), end.getTime()));

            if (staff != null) {
                crit.add(Restrictions.eq("staff", staff));
            }

            if (project != null) {
                crit.createCriteria("workPackage").add(Restrictions.eq("project", project));
            }

            crit.addOrder(Order.asc("day"));
            final List<Activity> activities = crit.list();
            if (logger.isDebugEnabled()) {
                logger.debug(activities.size() + " activities found");
            }
            return activities;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            final GregorianCalendar start = new GregorianCalendar(2008, 0, 1);
            final GregorianCalendar end = new GregorianCalendar(2018, 0, 28);

            final PreparedDBManager ob = new PreparedDBManager();
            final Staff st = new Staff();
            st.setId(2);
            final List<Activity> act = ob.getActivities(start, end, st, null);
            System.out.println("count: " + act.size());
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
}
