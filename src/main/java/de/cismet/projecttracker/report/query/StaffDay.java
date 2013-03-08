package de.cismet.projecttracker.report.query;


import de.cismet.projecttracker.report.db.entities.Project;
import de.cismet.projecttracker.report.helper.CalendarHelper;
import java.util.*;

/**
 * This class represents a single day of a staff and contains all activities, leave, illness time
 * and training, that a staff has done at the corresponding day.
 * @author therter
 */
public class StaffDay implements Comparable<StaffDay> {
    private Hashtable<Project, ProjectActivity> projectActivities = new Hashtable<Project, ProjectActivity>();
    private final static String HOLIDAY = "Urlaub";
    private final static String ILLNESS = "Krank";
    private double specialLeave;
    private double annualLeave;
    private double illness;
    private double training;
    private GregorianCalendar day;
    private GregorianCalendar start;
    private GregorianCalendar end;

    
    public StaffDay(Date day) {
        this.day = new GregorianCalendar(day.getYear() + 1900, day.getMonth(), day.getDate());
    }

    /**
     * @return the projectActivities
     */
    public ProjectActivity getProjectActivity(Project project) {
        return projectActivities.get(project);
    }

    /**
     * @param projectActivities the projectActivities to set
     */
    public void addProjectActivity(Project project, ProjectActivity projectActivity) {
        this.projectActivities.put(project, projectActivity);
    }

    /**
     * @return the specialLeave
     */
    public double getSpecialLeave() {
        return specialLeave;
    }

    /**
     * @param specialLeave the specialLeave to set
     */
    public void addSpecialLeave(double hours) {
        this.specialLeave = hours;
    }

    /**
     * @return the annualLeave
     */
    public double getAnnualLeave() {
        return annualLeave;
    }

    /**
     * @param annualLeave the annualLeave to set
     */
    public void addAnnualLeave(double hours) {
        this.annualLeave += hours;
    }

    /**
     * @return the illness
     */
    public double getIllness() {
        return illness;
    }

    /**
     * @param illness the illness to set
     */
    public void addIllness(double hours) {
        this.illness += hours;
    }

    /**
     * @return the training
     */
    public double getTraining() {
        return training;
    }

    /**
     * @param training the training to set
     */
    public void addTraining(double hours) {
        this.training += hours;
    }


    public List<ProjectActivity> getAllProjectActivities() {
        Vector<ProjectActivity> result = new Vector<ProjectActivity>();

        result.addAll(projectActivities.values());
        
        return result;
    }

    /**
     * @return the start
     */
    public GregorianCalendar getStart() {
        return start;
    }

    /**
     * @return the start
     */
    public String getStartAsString() {
        return CalendarHelper.toDateString( start );
    }    
    
    /**
     * @param start the start to set
     */
    public void setStart(GregorianCalendar start) {
        this.start = start;
    }
    
    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = new GregorianCalendar(start.getYear() + 1900, start.getMonth(), start.getDate(), start.getHours(), start.getMinutes());
    }

    /**
     * @return the end
     */
    public GregorianCalendar getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(GregorianCalendar end) {
        this.end = end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = new GregorianCalendar(end.getYear() + 1900, end.getMonth(), end.getDate(), end.getHours(), end.getMinutes());
    }
    
    public List<String> getErrors() {
        //TODO: internationalisieren
        List<String> errors = new ArrayList<String>();
        double time = 0.0;

        for (Project tmp : projectActivities.keySet()) {
            ProjectActivity act = projectActivities.get(tmp);
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
        
        if (start != null && end != null) {
            GregorianCalendar testTime = (GregorianCalendar)start.clone();
            int amount = (int)(time * 60);
            testTime.add(GregorianCalendar.MINUTE, amount);
            long diff = (end.getTimeInMillis() - start.getTimeInMillis()) / 60000;
            if (diff > 30) {
                errors.add(CalendarHelper.toDateString(day) + ": Die Differenz zwischen der Start- und Endzeit und den gebuchten Stunden beträgt " + diff + " Minuten.");
            }
        }
        
        return errors;
    }

    @Override
    public int compareTo(StaffDay o) {
        return day.compareTo(o.day);
    }
}
