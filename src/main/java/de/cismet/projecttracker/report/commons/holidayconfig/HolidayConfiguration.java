/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.projecttracker.report.commons.holidayconfig;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class HolidayConfiguration {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger logger = Logger.getLogger(HolidayConfiguration.class);

    //~ Instance fields --------------------------------------------------------

    private List<HolidayConfItem> holidayList = new ArrayList<HolidayConfItem>();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  file  DOCUMENT ME!
     */
    public void loadConfiguration(final InputStream file) {
        try {
            final DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            fac.setIgnoringComments(true);
            fac.setIgnoringElementContentWhitespace(true);
            fac.setNamespaceAware(true);
            fac.setCoalescing(true);
            fac.setExpandEntityReferences(false);

            final DocumentBuilder builder = fac.newDocumentBuilder();
            final Document doc = builder.parse(file);
            final Node rootElement = doc.getChildNodes().item(0);
            final NodeList nodes = rootElement.getChildNodes();

            for (int i = 0; i < nodes.getLength(); ++i) {
                final Node tmp = nodes.item(i);
                if ((tmp.getLocalName() != null) && tmp.getLocalName().equals("holiday")) {
                    final HolidayConfItem tmpItem = new HolidayConfItem();
                    final NodeList holidayElements = tmp.getChildNodes();

                    for (int n = 0; n < holidayElements.getLength(); ++n) {
                        final Node tmpNode = holidayElements.item(n);

                        if (tmpNode.getLocalName() != null) {
                            if (tmpNode.getLocalName().equals("name")) {
                                tmpItem.setName(tmpNode.getTextContent());
                            } else if (tmpNode.getLocalName().equals("isHalfHoliday")) {
                                tmpItem.setHalfHoliday(Boolean.valueOf(tmpNode.getTextContent()).booleanValue());
                            } else if (tmpNode.getLocalName().equals("time")) {
                                final HolidayTime holidayTime = getTime(tmpNode);
                                if (holidayTime != null) {
                                    tmpItem.setTime(holidayTime);
                                }
                            }
                        }
                    }
                    if (tmpItem.isValid()) {
//                        System.out.println(tmpItem.toString());
                        holidayList.add(tmpItem);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   timeNode  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ParseException  DOCUMENT ME!
     */
    private HolidayTime getTime(final Node timeNode) throws ParseException {
        final HolidayTime holidayTime = new HolidayTime();
        final NodeList timeNodes = timeNode.getChildNodes();

        for (int k = 0; k < timeNodes.getLength(); ++k) {
            final String nodeName = timeNodes.item(k).getLocalName();
            if ((nodeName != null) && nodeName.equals("dayAfterEaster")) {
                holidayTime.setDaysAfterEaster(Integer.parseInt(timeNodes.item(k).getTextContent()));
                holidayTime.setEveryYear(true);
            } else if ((nodeName != null) && nodeName.equals("fixDate")) {
                final NodeList fixDateNodes = timeNodes.item(k).getChildNodes();

                for (int l = 0; l < fixDateNodes.getLength(); ++l) {
                    if (fixDateNodes.item(l).getLocalName() != null) {
                        if (fixDateNodes.item(l).getLocalName().equals("date")) {
                            final GregorianCalendar cal = new GregorianCalendar();
                            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            final Date time = formatter.parse(fixDateNodes.item(l).getTextContent());
                            cal.setTime(time);
                            holidayTime.setFixDate(cal);
                        } else if (fixDateNodes.item(l).getLocalName().equals("isEveryYear")) {
                            holidayTime.setEveryYear(Boolean.parseBoolean(fixDateNodes.item(l).getTextContent()));
                        }
                    }
                }
            }
        }

        return holidayTime;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the holidayList
     */
    public List<HolidayConfItem> getHolidayList() {
        return holidayList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  holidayList  the holidayList to set
     */
    public void setHolidayList(final List<HolidayConfItem> holidayList) {
        this.holidayList = holidayList;
    }
}
