package de.cismet.projecttracker.report.commons;

import de.cismet.projecttracker.report.commons.holidayconfig.HolidayConfItem;
import de.cismet.projecttracker.report.commons.holidayconfig.HolidayConfiguration;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;



public class HolidayEvaluator {
    private static Logger logger = Logger.getLogger("de.cismet.projecttracker.report.commons.Holidays");
    private static final String CONFIG_FILE = "/de/cismet/projecttracker/report/commons/holidayConfig.xml";
    private Vector<Holiday> holidays;
    private int year;
    public final static int HOLIDAY = 0;
    public final static int HALF_HOLIDAY = 1;
    public final static int WORKDAY = -1;


    public HolidayEvaluator() {
        this.year = 0;
        holidays = null;
    }


    /**
     *	liefert -1, wenn der uebergebene Tag kein Feiertag ist,
     *			 0 wenn der uebergebene Tag ein Feiertag ist
     *			 1 wenn der uebergebene Tag ein halber Feiertag ist.
     */
    public int isHoliday(GregorianCalendar day) {
        Holiday hol = getHolidayForDay(day);

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

    
    public String getNameOfHoliday(GregorianCalendar day) {
        Holiday hol = getHolidayForDay(day);

        if (hol != null) {
            return hol.getName();
        } else {
            return null;
        }
    }

    
    private Holiday getHolidayForDay(GregorianCalendar day) {
        if (day.get(GregorianCalendar.YEAR) != this.year) {
            calculateHolidays(day.get(GregorianCalendar.YEAR));
        }

        int index = Collections.binarySearch(this.holidays, new Holiday(day));

        if (index >= 0) {
            Holiday hol = (Holiday) holidays.elementAt(index);
            return hol;
        } else {
            return null;
        }
    }
    
    /**
     *	berechnet alle beweglichen Feiertage des Saarlandes und speichert diese im Vector holidays.
     *	year bezeichnet das Jahr, fuer die die Feiertageberechnet werden sollen.
     */
    private void calculateHolidays(int year) {
        Holiday currentHoliday;
        GregorianCalendar easter = getEaster(year);
        GregorianCalendar tmp;
        this.year = year;
        holidays = new Vector<Holiday>();

        HolidayConfiguration config = getConfiguration();
        List<HolidayConfItem> holidayList = config.getHolidayList();

        for (HolidayConfItem singleHoliday : holidayList) {
            currentHoliday = new Holiday( singleHoliday.getName() );
            tmp = new GregorianCalendar();

            if (singleHoliday.isHalfHoliday()) {
                currentHoliday.setIsHalfDay(true);
            }

            if (singleHoliday.getTime().getFixDate() != null ) {
                tmp.setTimeInMillis( singleHoliday.getTime().getFixDate().getTimeInMillis() );

                if ( singleHoliday.getTime().isEveryYear() ) {	//Feiertag ist jedes Jahr
                    tmp.set(GregorianCalendar.YEAR, year);
                } else {
                    if (tmp.get(GregorianCalendar.YEAR) != year) {
                        tmp = null;						//Feiertag liegt nicht im geforderten Jahr
                    }
                }
            } else {
                //Feiertag abhaengig von Ostern
                tmp = (GregorianCalendar) easter.clone();
                tmp.add( GregorianCalendar.DATE, singleHoliday.getTime().getDaysAfterEaster() );
            }
            if (tmp != null) {
                currentHoliday.setDate(tmp);
                this.holidays.add(currentHoliday);
            }
        }

        Collections.sort(this.holidays);
    }

    /**
     *	berechnet den Ostersonntag und liefert diesen als Rueckgabewert
     */
    private GregorianCalendar getEaster(int year) {
        int D;
        GregorianCalendar easter;
        D = (((255 - 11 * (year % 19)) - 21) % 30) + 21;
        easter = new GregorianCalendar(year, 3 - 1, 1);
        int days = D + ((D > 48) ? 1 : 0) + 6 - ((year + year / 4 + D + ((D > 48) ? 1 : 0) + 1) % 7);
        easter.add(GregorianCalendar.DATE, days);
        return easter;
    }


    private HolidayConfiguration getConfiguration() {
        HolidayConfiguration configFile = null;
        InputStream is = getClass().getResourceAsStream(CONFIG_FILE);

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

