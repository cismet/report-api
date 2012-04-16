package de.cismet.projecttracker.report.query;

import de.cismet.projecttracker.report.db.entities.CostCategory;
import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.db.entities.WorkPackage;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;


/**
 * This class represents a project activity
 * @author therter
 */
public class ProjectActivity {
    private static Logger logger = Logger.getLogger(ProjectActivity.class);
    private Project project;
    private Hashtable<CostCategory, Double> hoursOfWork = new Hashtable<CostCategory, Double>();
    private Hashtable<CostCategory, Vector<WorkPackage>> workPackages = new Hashtable<CostCategory, Vector<WorkPackage>>();
    private Hashtable<WorkPackage, Double> workPackagesHours = new Hashtable<WorkPackage, Double>();
    private double hours;


    public ProjectActivity() {}

    public ProjectActivity(Project project) {
        this.project = project;
    }

    /**
     * adds the given working hours to the activity
     * @param workCategory the work category, the hours should be assigned to
     * @param workpackage the workpackage, the hours were worked in. This can also be null
     * @param hours the working hours
     */
    public void addHours(CostCategory costCategory, WorkPackage workpackage, double hours) {
        if (logger.isDebugEnabled()) {
            logger.debug("add to workCategory " + costCategory + " for work package " + workpackage + " " + hours + " h");
        }

        this.hours += hours;

        // add the hours of work ordered by cost categories
        if (costCategory != null) {
            Double work = hoursOfWork.get(costCategory);

            if (work == null) {
                work = new Double(0.0);
            }

            work = new Double(work.doubleValue() + hours);
            hoursOfWork.put(costCategory, work);

            // adds the work package to the cost category
            Vector<WorkPackage> workPackageList = workPackages.get(costCategory);

            if (workPackageList == null) {
                workPackageList = new Vector<WorkPackage>();
            }

            if (workpackage != null && !workPackageList.contains(workpackage)) {
                workPackageList.add(workpackage);
            }
        }

        // adds the hours of work ordered by work packages
        if (workpackage != null) {
            Double wpHours = workPackagesHours.get(workpackage);

            if (wpHours == null) {
                wpHours = new Double(0.0);
            }

            wpHours = new Double( wpHours.doubleValue() + hours );

            workPackagesHours.put(workpackage, wpHours);
        }
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }


    public double getHoursOfWork() {
        return hours;
    }

    public double getHoursOfWorkForCostCategory(CostCategory category) {
        Double result = hoursOfWork.get(category);
        return ( result == null ? 0.0 : result);
    }


    public double getHoursOfWorkForWorkPackage(WorkPackage wp) {
        Double result = workPackagesHours.get(wp);
        return ( result == null ? 0.0 : result);
    }


    public List<WorkPackage> getWorkPackagesForCostCategory(CostCategory category) {
        return workPackages.get(category);
    }


    /**
     * @return a list with all cost categories, the employee worked in.
     */
    public List<CostCategory> getCostCategories() {
        Enumeration<CostCategory> en = hoursOfWork.keys();
        List<CostCategory> result = new Vector<CostCategory>();

        while ( en.hasMoreElements()) {
            result.add( en.nextElement() );
        }

        return result;
    }

    /**
     * @return a list with all work packages, the employee worked in.
     */
    public List<WorkPackage> getWorkPackages() {
        Enumeration<WorkPackage> en = workPackagesHours.keys();
        List<WorkPackage> result = new Vector<WorkPackage>();

        while ( en.hasMoreElements()) {
            result.add( en.nextElement() );
        }

        return result;
    }
}
