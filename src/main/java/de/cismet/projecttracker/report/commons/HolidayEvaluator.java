/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.commons;

import bsh.This;

import org.apache.log4j.Logger;

import java.io.InputStream;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import de.cismet.projecttracker.report.commons.holidayconfig.HolidayConfItem;
import de.cismet.projecttracker.report.commons.holidayconfig.HolidayConfiguration;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class HolidayEvaluator {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger("de.cismet.projecttracker.report.commons.Holidays");
    private static final String CONFIG_FILE = "/de/cismet/projecttracker/report/commons/holidayConfig.xml";
    public static final int HOLIDAY = 0;
    public static final int HALF_HOLIDAY = 1;
    public static final int WORKDAY = -1;

    //~ Instance fields --------------------------------------------------------

    private Vector<Holiday> holidays;
    private int year;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new HolidayEvaluator object.
     */
    public HolidayEvaluator() {
        this.year = 0;
        holidays = null;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * liefert -1, wenn der uebergebene Tag kein Feiertag ist, 0 wenn der uebergebene Tag ein Feiertag ist 1 wenn der
     * uebergebene Tag ein halber Feiertag ist.
     *
     * @param   day  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int isHoliday(final GregorianCalendar day) {
        final Holiday hol = getHolidayForDay(day);

        if (hol != null) {
            if (hol.isHalfDay()) {
                return HALF_HOLIDAY;
            } else {
                return HOLIDAY;
            }
        } else {
            return WORKDAY;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   day  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getNameOfHoliday(final GregorianCalendar day) {
        final Holiday hol = getHolidayForDay(day);

        if (hol != null) {
            return hol.getName();
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   day  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Holiday getHolidayForDay(final GregorianCalendar day) {
        if (day.get(GregorianCalendar.YEAR) != this.year) {
            calculateHolidays(day.get(GregorianCalendar.YEAR));
        }

        final int index = Collections.binarySearch(this.holidays, new Holiday(day));

        if (index >= 0) {
            final Holiday hol = (Holiday)holidays.elementAt(index);
            return hol;
        } else {
            return null;
        }
    }

    /**
     * berechnet alle beweglichen Feiertage des Saarlandes und speichert diese im Vector holidays. year bezeichnet das
     * Jahr, fuer die die Feiertageberechnet werden sollen.
     *
     * @param  year  DOCUMENT ME!
     */
    private void calculateHolidays(final int year) {
        Holiday currentHoliday;
        final GregorianCalendar easter = getEaster(year);
        GregorianCalendar tmp;
        this.year = year;
        holidays = new Vector<Holiday>();

        final HolidayConfiguration config = getConfiguration();
        final List<HolidayConfItem> holidayList = config.getHolidayList();

        for (final HolidayConfItem singleHoliday : holidayList) {
            currentHoliday = new Holiday(singleHoliday.getName());
            tmp = new GregorianCalendar();

            if (singleHoliday.isHalfHoliday()) {
                currentHoliday.setIsHalfDay(true);
            }

            if (singleHoliday.getTime().getFixDate() != null) {
                tmp.setTimeInMillis(singleHoliday.getTime().getFixDate().getTimeInMillis());

                if (singleHoliday.getTime().isEveryYear()) { // Feiertag ist jedes Jahr
                    tmp.set(GregorianCalendar.YEAR, year);
                } else {
                    if (tmp.get(GregorianCalendar.YEAR) != year) {
                        tmp = null;                          // Feiertag liegt nicht im geforderten Jahr
                    }
                }
            } else {
                // Feiertag abhaengig von Ostern
                tmp = (GregorianCalendar)easter.clone();
                tmp.add(GregorianCalendar.DATE, singleHoliday.getTime().getDaysAfterEaster());
            }
            if (tmp != null) {
                currentHoliday.setDate(tmp);
                this.holidays.add(currentHoliday);
            }
        }

        Collections.sort(this.holidays);
    }

    /**
     * calculates the number of holidays between 2 Dates (including the interval bounds).
     *
     * @param   fromDate  DOCUMENT ME!
     * @param   toDate    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getNumberOfHolidaysOnWeekDays(final GregorianCalendar fromDate, final GregorianCalendar toDate) {
        // calculate the number of years
        final int fromYear = fromDate.get(GregorianCalendar.YEAR);
        final int toYear = toDate.get(GregorianCalendar.YEAR);
        int holidays = 0;

        for (int i = fromYear; i <= toYear; i++) {
            if (i != this.year) {
                calculateHolidays(i);
            }
            holidays += this.holidays.size();
            // dont count Holidays that are on saturday or sunday
            if ((i != fromYear) && (i != toYear)) {
                for (final Holiday h : this.holidays) {
                    final int dayOfWeek = h.getDate().get(GregorianCalendar.DAY_OF_WEEK);

                    if ((dayOfWeek == GregorianCalendar.SATURDAY) || (dayOfWeek == GregorianCalendar.SUNDAY)) {
                        holidays--;
                    }
                }
            }

            // only add holidays before the toDate and after fromDate...
            if (i == fromYear) {
                final int insertionIndex = Collections.binarySearch(this.holidays, new Holiday(fromDate));
                if (insertionIndex >= 0) {
                    // holiday found
                    holidays -= insertionIndex;
                } else {
                    holidays -= -(insertionIndex + 1);
                }

                for (int j = (insertionIndex >= 0) ? insertionIndex : -(insertionIndex + 1); j < this.holidays.size();
                            j++) {
                    final Holiday h = this.holidays.get(j);
                    final int dayOfWeek = h.getDate().get(GregorianCalendar.DAY_OF_WEEK);
                    if ((dayOfWeek == GregorianCalendar.SATURDAY) || (dayOfWeek == GregorianCalendar.SUNDAY)) {
                        if (h.getDate().before(toDate)) {
                            holidays--;
                        }
                    }
                }
            }
            if (i == toYear) {
                final int insertionIndex = Collections.binarySearch(this.holidays, new Holiday(toDate));

                if (insertionIndex >= 0) {
                    // holiday found
                    holidays -= (this.holidays.size() - (insertionIndex + 1));
                } else {
                    holidays -= this.holidays.size() + (insertionIndex + 1);
                }

                for (int j = (insertionIndex >= 0) ? insertionIndex : -insertionIndex; j >= 0; j--) {
                    final Holiday h = this.holidays.get(j);
                    final int dayOfWeek = h.getDate().get(GregorianCalendar.DAY_OF_WEEK);
                    if ((dayOfWeek == GregorianCalendar.SATURDAY) || (dayOfWeek == GregorianCalendar.SUNDAY)) {
                        if (h.getDate().after(fromDate)) {
                            holidays--;
                        }
                    }
                }
            }

        }

        return holidays;
    }

    /**
     * berechnet den Ostersonntag und liefert diesen als Rueckgabewert.
     *
     * @param   year  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private GregorianCalendar getEaster(final int year) {
        final int D;
        final GregorianCalendar easter;
        D = (((255 - (11 * (year % 19))) - 21) % 30) + 21;
        easter = new GregorianCalendar(year, 3 - 1, 1);
        final int days = D + ((D > 48) ? 1 : 0) + 6 - ((year + (year / 4) + D + ((D > 48) ? 1 : 0) + 1) % 7);
        easter.add(GregorianCalendar.DATE, days);
        return easter;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private HolidayConfiguration getConfiguration() {
        HolidayConfiguration configFile = null;
        final InputStream is = getClass().getResourceAsStream(CONFIG_FILE);

//        BufferedReader br = new BufferedReader( new InputStreamReader(is) );
//        String tmp = "";
//        try {
//            while ( (tmp = br.readLine()) != null ) {
//                System.out.println("1 " + tmp);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException ex) {}
//            }
//            is = getClass().getResourceAsStream(CONFIG_FILE);
//        }

        if (is != null) {
            configFile = new HolidayConfiguration();
            configFile.loadConfiguration(is);
        } else {
            logger.error("holiday configuration file not found");
        }

        if (configFile == null) {
            configFile = new HolidayConfiguration();
        }

        return configFile;
    }
}
