/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.query;

import java.util.List;
import java.util.Vector;

import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.db.entities.WorkCategory;
import de.cismet.projecttracker.report.db.entities.WorkPackage;

/**
 * This class represents a single day of a projeect and contains all activities, which was done for the project at the
 * corresponding day.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ProjectDay {

    //~ Instance fields --------------------------------------------------------

    private Vector<Activity> projectActivities = new Vector<Activity>();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  projectActivity  the projectActivities to set
     */
    public void addProjectActivity(final Activity projectActivity) {
        this.projectActivities.add(projectActivity);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<Activity> getAllProjectActivities() {
        final Vector<Activity> result = new Vector<Activity>();

        result.addAll(projectActivities);

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wp  if wp is null, the working hours of all work packages will be returned
     * @param   wc  if work category is null, the working hours of all work categories will be returned
     *
     * @return  the entire hours of work for all activities of the project day, which are assigned to the given work
     *          package
     */
    public double getHoursOfWork(final WorkPackage wp, final WorkCategory wc) {
        double hours = 0.0;

        for (final Activity tmp : projectActivities) {
            if ((wc == null) || wc.equals(tmp.getWorkCategory())) {
                if ((wp == null) || wp.equals(tmp.getWorkPackage())) {
                    hours += tmp.getWorkinghours();
                }
            }
        }

        return hours;
    }
}
