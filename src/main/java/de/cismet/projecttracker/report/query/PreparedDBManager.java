package de.cismet.projecttracker.report.query;

import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.db.entities.Staff;
import de.cismet.projecttracker.report.exceptions.DataRetrievalException;
import de.cismet.projecttracker.report.helper.CalendarHelper;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author therter
 */
public class PreparedDBManager extends DBManager {
    private static final String CONTRACT_QUERY = "select distinct contract from Contract as contract where contract.staff = %d " +
                                                 "and (contract.todate=null or contract.todate>='%s') and contract.fromdate<='%s'";
    private static Logger logger = Logger.getLogger(PreparedDBManager.class);

    
    /**
     * reads all activity of the given user for the given time period from the database,
     * which are not only for internal use
     *
     * @param start the start of the time period, the activities should be in
     * @param end the end of the time period, the activities should be in
     * @param staff the staff, the activities should be from. If the staff is null, the activities of all staff will be returned
     * @param project the project, the activities should be assigned to or if the project should not be considered
     * @param declaration this parameter determines, whether internal activities should be considered
     * @return the corresponding activities sorted by day in ascending order
     * @throws DataRetrievalException
     */
    public List<Activity> getActivities(GregorianCalendar start, GregorianCalendar end, Staff staff, Project project) throws DataRetrievalException {
        try {
            Criteria crit = hibernateSession.createCriteria(Activity.class).add( Restrictions.between("day", start.getTime(), end.getTime()) );

            if ( staff != null ) {
                crit.add( Restrictions.eq("staff", staff) );
            }

            if ( project != null ) {
                crit.createCriteria("workPackage").add( Restrictions.eq("project", project) );
            }

            crit.addOrder( Order.asc("day") );
            List<Activity> activities = crit.list();

            logger.debug(activities.size() + " activities found");
            return activities;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t.getMessage(), t);
        }
    }


    public static void main(String[] args) {
        try {
            GregorianCalendar start = new GregorianCalendar(2008, 0, 1);
            GregorianCalendar end = new GregorianCalendar(2018, 0, 28);

            PreparedDBManager ob = new PreparedDBManager();
            Staff st = new Staff();
            st.setId(2);
            List<Activity> act = ob.getActivities(start, end, st, null);
            System.out.println("count: " + act.size());
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
}
