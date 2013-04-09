/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.query;

import org.apache.log4j.Logger;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import de.cismet.projecttracker.report.db.entities.CostCategory;
import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.db.entities.WorkPackage;

/**
 * This class represents a project activity.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ProjectActivity {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger(ProjectActivity.class);

    //~ Instance fields --------------------------------------------------------

    private Project project;
    private Hashtable<CostCategory, Double> hoursOfWork = new Hashtable<CostCategory, Double>();
    private Hashtable<CostCategory, Vector<WorkPackage>> workPackages =
        new Hashtable<CostCategory, Vector<WorkPackage>>();
    private Hashtable<WorkPackage, Double> workPackagesHours = new Hashtable<WorkPackage, Double>();
    private double hours;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ProjectActivity object.
     */
    public ProjectActivity() {
    }

    /**
     * Creates a new ProjectActivity object.
     *
     * @param  project  DOCUMENT ME!
     */
    public ProjectActivity(final Project project) {
        this.project = project;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * adds the given working hours to the activity.
     *
     * @param  costCategory  the work category, the hours should be assigned to
     * @param  workpackage   the workpackage, the hours were worked in. This can also be null
     * @param  hours         the working hours
     */
    public void addHours(final CostCategory costCategory, final WorkPackage workpackage, final double hours) {
        if (logger.isDebugEnabled()) {
            logger.debug("add to workCategory " + costCategory + " for work package " + workpackage + " " + hours
                        + " h");
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

            if ((workpackage != null) && !workPackageList.contains(workpackage)) {
                workPackageList.add(workpackage);
            }
        }

        // adds the hours of work ordered by work packages
        if (workpackage != null) {
            Double wpHours = workPackagesHours.get(workpackage);

            if (wpHours == null) {
                wpHours = new Double(0.0);
            }

            wpHours = new Double(wpHours.doubleValue() + hours);

            workPackagesHours.put(workpackage, wpHours);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  project  the project to set
     */
    public void setProject(final Project project) {
        this.project = project;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getHoursOfWork() {
        return hours;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   category  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getHoursOfWorkForCostCategory(final CostCategory category) {
        final Double result = hoursOfWork.get(category);
        return ((result == null) ? 0.0 : result);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   wp  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getHoursOfWorkForWorkPackage(final WorkPackage wp) {
        final Double result = workPackagesHours.get(wp);
        return ((result == null) ? 0.0 : result);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   category  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<WorkPackage> getWorkPackagesForCostCategory(final CostCategory category) {
        return workPackages.get(category);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  a list with all cost categories, the employee worked in.
     */
    public List<CostCategory> getCostCategories() {
        final Enumeration<CostCategory> en = hoursOfWork.keys();
        final List<CostCategory> result = new Vector<CostCategory>();

        while (en.hasMoreElements()) {
            result.add(en.nextElement());
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  a list with all work packages, the employee worked in.
     */
    public List<WorkPackage> getWorkPackages() {
        final Enumeration<WorkPackage> en = workPackagesHours.keys();
        final List<WorkPackage> result = new Vector<WorkPackage>();

        while (en.hasMoreElements()) {
            result.add(en.nextElement());
        }

        return result;
    }
}
