/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.query;

import java.util.*;

import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.helper.CalendarHelper;

/**
 * This class represents a single day of a staff and contains all activities, leave, illness time and training, that a
 * staff has done at the corresponding day.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class StaffDay implements Comparable<StaffDay> {

    //~ Static fields/initializers ---------------------------------------------

    private static final String HOLIDAY = "Urlaub";
    private static final String ILLNESS = "Krank";

    //~ Instance fields --------------------------------------------------------

    private Hashtable<Project, ProjectActivity> projectActivities = new Hashtable<Project, ProjectActivity>();
    private double specialLeave;
    private double annualLeave;
    private double illness;
    private double training;
    private GregorianCalendar day;
    private GregorianCalendar start;
    private GregorianCalendar end;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StaffDay object.
     *
     * @param  day  DOCUMENT ME!
     */
    public StaffDay(final Date day) {
        this.day = new GregorianCalendar(day.getYear() + 1900, day.getMonth(), day.getDate());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   project  DOCUMENT ME!
     *
     * @return  the projectActivities
     */
    public ProjectActivity getProjectActivity(final Project project) {
        return projectActivities.get(project);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  project          DOCUMENT ME!
     * @param  projectActivity  the projectActivities to set
     */
    public void addProjectActivity(final Project project, final ProjectActivity projectActivity) {
        this.projectActivities.put(project, projectActivity);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the specialLeave
     */
    public double getSpecialLeave() {
        return specialLeave;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hours  specialLeave the specialLeave to set
     */
    public void addSpecialLeave(final double hours) {
        this.specialLeave = hours;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the annualLeave
     */
    public double getAnnualLeave() {
        return annualLeave;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hours  annualLeave the annualLeave to set
     */
    public void addAnnualLeave(final double hours) {
        this.annualLeave += hours;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the illness
     */
    public double getIllness() {
        return illness;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hours  illness the illness to set
     */
    public void addIllness(final double hours) {
        this.illness += hours;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the training
     */
    public double getTraining() {
        return training;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hours  training the training to set
     */
    public void addTraining(final double hours) {
        this.training += hours;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<ProjectActivity> getAllProjectActivities() {
        final Vector<ProjectActivity> result = new Vector<ProjectActivity>();

        result.addAll(projectActivities.values());

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the start
     */
    public GregorianCalendar getStart() {
        return start;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the start
     */
    public String getStartAsString() {
        return CalendarHelper.toDateString(start);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  start  the start to set
     */
    public void setStart(final GregorianCalendar start) {
        this.start = start;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  start  the start to set
     */
    public void setStart(final Date start) {
        this.start = new GregorianCalendar(start.getYear() + 1900,
                start.getMonth(),
                start.getDate(),
                start.getHours(),
                start.getMinutes());
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the end
     */
    public GregorianCalendar getEnd() {
        return end;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  end  the end to set
     */
    public void setEnd(final GregorianCalendar end) {
        this.end = end;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  end  the end to set
     */
    public void setEnd(final Date end) {
        this.end = new GregorianCalendar(end.getYear() + 1900,
                end.getMonth(),
                end.getDate(),
                end.getHours(),
                end.getMinutes());
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<String> getErrors() {
        // TODO: internationalisieren
        final List<String> errors = new ArrayList<String>();
        double time = 0.0;

        for (final Project tmp : projectActivities.keySet()) {
            final ProjectActivity act = projectActivities.get(tmp);
            time += act.getHoursOfWork();
            if (act.getProject().getName().equals(HOLIDAY) || act.getProject().getName().equals(ILLNESS)) {
                return null;
            }
        }

        if (start == null) {
            errors.add(CalendarHelper.toDateString(day) + ": Startzeit fehlt");
        }

        if (end == null) {
            errors.add(CalendarHelper.toDateString(day) + ": Endzeit fehlt");
        }

        if ((start != null) && (end != null)) {
            final GregorianCalendar testTime = (GregorianCalendar)start.clone();
            final int amount = (int)(time * 60);
            testTime.add(GregorianCalendar.MINUTE, amount);
            final long diff = (end.getTimeInMillis() - start.getTimeInMillis()) / 60000;
            if (diff > 30) {
                errors.add(CalendarHelper.toDateString(day)
                            + ": Die Differenz zwischen der Start- und Endzeit und den gebuchten Stunden beträgt "
                            + diff + " Minuten.");
            }
        }

        return errors;
    }

    @Override
    public int compareTo(final StaffDay o) {
        return day.compareTo(o.day);
    }
}
