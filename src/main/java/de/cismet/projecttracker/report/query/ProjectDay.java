package de.cismet.projecttracker.report.query;

import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.db.entities.WorkCategory;
import de.cismet.projecttracker.report.db.entities.WorkPackage;
import java.util.List;
import java.util.Vector;

/**
 * This class represents a single day of a projeect and contains all activities,
 * which was done for the project at the corresponding day.
 * @author therter
 */
public class ProjectDay {
    private Vector<Activity> projectActivities = new Vector<Activity>();

    /**
     * @param projectActivities the projectActivities to set
     */
    public void addProjectActivity(Activity projectActivity) {
        this.projectActivities.add(projectActivity);
    }

    
    public List<Activity> getAllProjectActivities() {
        Vector<Activity> result = new Vector<Activity>();

        result.addAll(projectActivities);

        return result;
    }

    
    /**
     *
     * @param wp if wp is null, the working hours of all work packages will be returned
     * @param wc if work category is null, the working hours of all work categories will be returned
     * @return the entire hours of work for all activities of the project day,
     *          which are assigned to the given work package
     */
    public double getHoursOfWork(WorkPackage wp, WorkCategory wc) {
        double hours = 0.0;

        for (Activity tmp : projectActivities) {
            if (wc == null || wc.equals(tmp.getWorkCategory())) {
                if (wp == null || wp.equals(tmp.getWorkPackage())) {
                    hours += tmp.getWorkinghours();
                }
            }
        }

        return hours;
    }
}
