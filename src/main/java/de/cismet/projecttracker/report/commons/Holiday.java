package de.cismet.projecttracker.report.commons;

import java.util.GregorianCalendar;

public class Holiday implements Comparable<Holiday> {
    private String name;
    private GregorianCalendar date;
    private boolean isHalfDay;


    public Holiday() {
        this(null, null, false);
    }

    
    public Holiday(String name) {
        this(name, null, false);
    }


    public Holiday(GregorianCalendar date) {
        this(null, date, false);
    }


    public Holiday(String name, GregorianCalendar date, boolean isHalfDay) {
        this.name = name;

        if (date != null) {
            this.date = (GregorianCalendar) date.clone();
        } else {
            date = null;
        }

        this.isHalfDay = isHalfDay;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setDate(GregorianCalendar date) {
        this.date = (GregorianCalendar) date.clone();
    }


    public void setIsHalfDay(boolean isHalfDay) {
        this.isHalfDay = isHalfDay;
    }


    public String getName() {
        return name;
    }


    public GregorianCalendar getDate() {
        return date;
    }


    public boolean isHalfDay() {
        return isHalfDay;
    }


    @Override
    public int compareTo(Holiday o) {
        if (this.date.get(GregorianCalendar.YEAR) == o.getDate().get(GregorianCalendar.YEAR) &&
                this.date.get(GregorianCalendar.MONTH) == o.getDate().get(GregorianCalendar.MONTH) &&
                this.date.get(GregorianCalendar.DATE) == o.getDate().get(GregorianCalendar.DATE)) {
            return 0;
        } else if (this.date.before(o.getDate())) {
            return -1;
        } else {
            return 1;
        }
    }
}
