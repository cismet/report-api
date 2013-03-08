/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.helper;

import de.cismet.projecttracker.report.db.entities.EstimatedComponentCost;
import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.db.entities.ProjectPeriod;
import de.cismet.projecttracker.report.db.entities.WorkPackage;
import de.cismet.projecttracker.report.db.entities.WorkPackagePeriod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class contains static method, which performs some common operations on database entities.
 *
 * @author therter
 */
public class QueryHelper {
    /**
     * @return the most recent version of the work package period or null, if no period exists
     */
    public static WorkPackagePeriod getMostRecentPeriod(WorkPackage wp) {
        WorkPackagePeriod currentPeriod = null;

        if (wp.getWorkPackagePeriods() != null && wp.getWorkPackagePeriods().size() > 0) {
            for (WorkPackagePeriod tmp : wp.getWorkPackagePeriods()) {
                if ( currentPeriod == null || tmp.getAsof().after( currentPeriod.getAsof() ) ) {
                    currentPeriod = tmp;
                }
            }
        }

        return currentPeriod;
    }


    /**
     * @return the most recent version of the project period or null, if no project period exists
     */
    public static ProjectPeriod getMostRecentPeriod(Project project) {
        ProjectPeriod currentPeriod = null;

        if (project.getProjectPeriods() != null && project.getProjectPeriods().size() > 0) {
            for (ProjectPeriod tmp : project.getProjectPeriods()) {
                if ( currentPeriod == null || tmp.getAsof().after( currentPeriod.getAsof() ) ) {
                    currentPeriod = tmp;
                }
            }
        }

        return currentPeriod;
    }

    /**
     * @return the most recent version of the project period or null, if no project period exists
     */
    public static EstimatedComponentCost getMostRecentEstimation(WorkPackage wk) {
        EstimatedComponentCost currentEstimation = null;

        if (wk.getEstimatedWorkPackageCosts() != null && wk.getWorkPackagePeriods().size() > 0) {
            for (EstimatedComponentCost tmp : wk.getEstimatedWorkPackageCosts()) {
                if ( currentEstimation == null || tmp.getCreationtime().after( currentEstimation.getCreationtime() ) ) {
                    currentEstimation = tmp;
                }
            }
        }

        return currentEstimation;
    }


    /**
     *
     * @param wk
     * @param time
     * @return the period, which are used at the given time by the given work package
     */
    public static WorkPackagePeriod getPeriodForTime(WorkPackage wp, GregorianCalendar time) {
        WorkPackagePeriod currentPeriod = null;

        if (wp.getWorkPackagePeriods() != null && wp.getWorkPackagePeriods().size() > 0) {
            List<WorkPackagePeriod> periods = new ArrayList<WorkPackagePeriod>();
            for (WorkPackagePeriod tmp : wp.getWorkPackagePeriods()) {
                periods.add(tmp);
            }
            Collections.sort( periods );
            int searchRes = Collections.binarySearch(periods, time);

            if (searchRes > 0) {
                currentPeriod = periods.get(searchRes);
            } else {
                searchRes = (searchRes + 1 ) * (-1);
                if (searchRes != 0) {
                    // if searchRes == 0, the first period that was created after the given time will be returned.
                    // Otherwise, the period, that was created closest before the given time will be returned.
                    --searchRes;
                }
                currentPeriod = periods.get( searchRes );
            }
        }

        return currentPeriod;
    }
}
