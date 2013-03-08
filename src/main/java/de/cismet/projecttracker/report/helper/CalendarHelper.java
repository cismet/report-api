package de.cismet.projecttracker.report.helper;

import de.cismet.projecttracker.report.commons.HolidayEvaluator;
import de.cismet.projecttracker.report.commons.TimePeriod;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author therter
 */
public class CalendarHelper {
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public static final SimpleDateFormat dateShortFormatter = new SimpleDateFormat("dd.MM.yy", Locale.US);
    public static final HolidayEvaluator holidays = new HolidayEvaluator();

    
    /**
     *
     * @param month
     * @return a new Calendar object that contains the first day of the given month
     */
    public static GregorianCalendar getFirstDayOfMonth(GregorianCalendar month) {
        GregorianCalendar firstDay = (GregorianCalendar)month.clone();
        firstDay.set(GregorianCalendar.DATE, 1);

        return firstDay;
    }


    /**
     *
     * @param month
     * @return a Calendar object that contains the last day of the given month
     */
    public static GregorianCalendar getLastDayOfMonth(GregorianCalendar month) {
        GregorianCalendar lastDay = (GregorianCalendar)month.clone();
        lastDay.set( GregorianCalendar.DATE, lastDay.getActualMaximum(GregorianCalendar.DATE) );

        return lastDay;
    }


    /**
     *
     * @param firstCal
     * @param secondCal
     * @return true, if and only if the date of the first calendar object is less or
     *          equal to the date of the second calendar object
     */
    public static boolean isMonthLessOrEqual(GregorianCalendar firstCal, GregorianCalendar secondCal) {
        if (firstCal.get(GregorianCalendar.YEAR) < secondCal.get(GregorianCalendar.YEAR)) {
            return true;
        } else if (firstCal.get(GregorianCalendar.YEAR) == secondCal.get(GregorianCalendar.YEAR)) {
            return ( firstCal.get(GregorianCalendar.MONTH) <= secondCal.get(GregorianCalendar.MONTH) );
        } else {
            return false;
        }
    }


    /**
     *
     * @param firstCal
     * @param secondCal
     * @return 
     */
    public static int compareDay(GregorianCalendar firstCal, GregorianCalendar secondCal) {
        long oneDay = (24*60*60*1000);
        long date1 = firstCal.getTimeInMillis() / oneDay;
        long date2 = secondCal.getTimeInMillis() / oneDay;

        return (int)Math.signum( date1 - date2 );
    }


    /**
     * The result calendar object has the same year and month, as the given month and
     * the last day of the given month, when the month is not equal to the month of the given end.
     * Else the result has the same day as the given end.
     *
     * @param month the month, the last day should calculated for
     * @param end the last day of the report
     * @return the day, that should be used as the last day of the given month
     */
    public static GregorianCalendar lastDayForMonth(GregorianCalendar month, GregorianCalendar end) {
        GregorianCalendar tmpEnd = (GregorianCalendar)month.clone();

        if (tmpEnd.get(GregorianCalendar.YEAR) == end.get(GregorianCalendar.YEAR) &&
                tmpEnd.get(GregorianCalendar.MONTH) == end.get(GregorianCalendar.MONTH)) {
            tmpEnd.set(GregorianCalendar.DAY_OF_MONTH, end.get(GregorianCalendar.DAY_OF_MONTH));
        } else {
            tmpEnd.set(GregorianCalendar.DAY_OF_MONTH, tmpEnd.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        }

        return tmpEnd;
    }

    
    /**
     * compares two calendar objects
     * @param date1
     * @param date2
     * @return true, if and only if date1 is less or equal to date2. The result
     *               only depends on the dates, which are contained in the given GregorianCalendar objects. The times will be ignored.
     */
    public static boolean isDateLessOrEqual(GregorianCalendar date1, GregorianCalendar date2) {
        int firstDate = date1.get(GregorianCalendar.YEAR) * 10000 + date1.get(GregorianCalendar.MONTH) * 100 + date1.get(GregorianCalendar.DATE);
        int secondDate = date2.get(GregorianCalendar.YEAR) * 10000 + date2.get(GregorianCalendar.MONTH) * 100 + date2.get(GregorianCalendar.DATE);

        return (firstDate <= secondDate);
    }
    
    /**
     * compares two calendar objects
     * @param date1
     * @param date2
     * @return true, if and only if date1 is less or equal to date2. The result
     *               only depends on the dates, which are contained in the given GregorianCalendar objects. The times will be ignored.
     */
    public static boolean isDateLess(GregorianCalendar date1, GregorianCalendar date2) {
        int firstDate = date1.get(GregorianCalendar.YEAR) * 10000 + date1.get(GregorianCalendar.MONTH) * 100 + date1.get(GregorianCalendar.DATE);
        int secondDate = date2.get(GregorianCalendar.YEAR) * 10000 + date2.get(GregorianCalendar.MONTH) * 100 + date2.get(GregorianCalendar.DATE);

        return (firstDate < secondDate);
    }


   /**
     * Set the given calendar object to the next working day.
     *
     * @param day
     */
    public static void setToNextWorkingDay(GregorianCalendar day) {
        do {
            day.add(GregorianCalendar.DATE, 1);
        } while ( !isWorkingDay(day) );
    }


    /**
     * Checks if the given calendar object represents a working day.
     * This means a day, that is no weekend and no holiday. At the moment, half holiday will be
     * ignored within this method.
     * @param day
     * @return
     */
    public static boolean isWorkingDay(GregorianCalendar day) {
        return ( day.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY  &&
                 day.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY &&
                 holidays.isHoliday(day) == HolidayEvaluator.WORKDAY);
    }

    /**
     * return 1 if the day is a working day, 0 if the day is no working day and 0.5 if the day is a half holiday
     */
    public static double isWorkingDayExactly(GregorianCalendar day) {
        if (day.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY  
                || day.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
            return 0.0;
        }
        int holiday = holidays.isHoliday(day);
        
        if (holiday == -1) {
            return 1.0;
        } else if (holiday == 0) {
            return 0.0;
        } else {
            return 0.5;
        }
    }
    
    /**
     * @param cal
     * @return a string representation of the given calendar object
     */
    public static String toDateString(GregorianCalendar cal) {
        return dateFormatter.format(cal.getTime());
    }

    
    public static String toDateString(Date date) {
        return dateFormatter.format(date);
    }

    public static String toDateString(TimePeriod period) {
        return toDateString(period.getStart()) + " - " + toDateString(period.getEnd());
    }


    public static String toDateStringShort(GregorianCalendar cal) {
        return dateShortFormatter.format(cal.getTime());
    }
}
