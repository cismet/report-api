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

package de.cismet.projecttracker.report;

import org.apache.log4j.Logger;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import de.cismet.projecttracker.report.commons.KeyConstants;
import de.cismet.projecttracker.report.db.entities.Activity;
import de.cismet.projecttracker.report.db.entities.Report;
import de.cismet.projecttracker.report.db.entities.Staff;
import de.cismet.projecttracker.report.exceptions.DataRetrievalException;
import de.cismet.projecttracker.report.exceptions.ReportNotFoundException;
import de.cismet.projecttracker.report.query.DBManager;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ReportManager {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger(ReportManager.class);

    //~ Instance fields --------------------------------------------------------

    private final String REPORT_PATH = "WEB-INF/reports";
    private final String REPORT_PACKAGE = "de.cismet.projecttracker.report";
    private String applicationPath;
    private ProjectTrackerReport[] reports;
    private URLClassLoader urlc;
    private ResourceBundle config;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new instance of PluginCore.
     *
     * @param  applicationPath  DOCUMENT ME!
     */
    public ReportManager(final String applicationPath) {
        this.applicationPath = applicationPath;
        initAvailableReports();
        this.config = ResourceBundle.getBundle("de.cismet.projecttracker.report.commons.ReportAPIConfig");
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * creates a new report und write the report into the db.
     *
     * @param   name        the name of the new report
     * @param   start       the start of the period, the report should created for
     * @param   end         the end of the period, the report should be created for
     * @param   staffID     the id of the staff, the report should created for
     * @param   reportName  the name of the report plugin that should be used to create the new report
     *
     * @return  the id of the newly creaed report
     *
     * @throws  DataRetrievalException   DOCUMENT ME!
     * @throws  ReportNotFoundException  DOCUMENT ME!
     */
    public long createReport(final String name,
            final GregorianCalendar start,
            final GregorianCalendar end,
            final long staffID,
            final String reportName) throws DataRetrievalException, ReportNotFoundException {
        final ProjectTrackerReport report = getReportByName(reportName);
        if (end.before(start)) {
            throw new DataRetrievalException(config.getString(KeyConstants.END_IS_BEFORE_START_KEY));
        }

        if (report != null) {
            byte[] createdReport = null;
            final List<Activity> activities = new ArrayList<Activity>();

            if (staffID == 0) {
                createdReport = report.generateReport(start, end, activities);
            } else {
                createdReport = report.generateReport(start, end, staffID, activities);
            }

            if (createdReport != null) {
                // todo das Speichern des Reports in die Report API auslagern
                // write the new report to the db
                final DBManager manager = new DBManager();
                final Report reportEntry = new Report();
                reportEntry.setCreationtime(new Date());
                reportEntry.setFromdate(start.getTime());
                reportEntry.setTodate(end.getTime());
                reportEntry.setName(name);
                reportEntry.setGeneratorname(report.getReportName());
                reportEntry.setReportdocument(createdReport);
                if (staffID != 0) {
                    final Staff staff = (Staff)manager.getObject(Staff.class, staffID);
                    reportEntry.setStaff(staff);
                }
                // add all activities, which was used for the creation of the report
                for (final de.cismet.projecttracker.report.db.entities.Activity tmp : activities) {
                    reportEntry.getActivities().add(tmp);
                }

                final long id = (Long)manager.createObject(reportEntry);
                manager.closeSession();

                return id;
            } else {
                throw new ReportNotFoundException(config.getString(KeyConstants.CANNOT_CREATE_REPORT_KEY));
            }
        } else {
            throw new ReportNotFoundException(config.getString(KeyConstants.REPORT_PLUGIN_NOT_FOUND_KEY));
        }
    }

    /**
     * gets all available plugins.
     *
     * @return  an array with instances of all available plugins
     */
    public ProjectTrackerReport[] getAvailableReports() {
        return reports;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   name  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ProjectTrackerReport getReportByName(final String name) {
        for (final ProjectTrackerReport tmp : reports) {
            if (tmp.getReportName().equals(name)) {
                return tmp;
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   document  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ProjectTrackerReport getReportByDocumentType(final byte[] document) {
        for (final ProjectTrackerReport tmp : reports) {
            if (tmp.canHandle(document)) {
                return tmp;
            }
        }

        return null;
    }

    /**
     * initializes the plugins array.
     */
    private void initAvailableReports() {
        final Vector<ProjectTrackerReport> pluginsVector = new Vector<ProjectTrackerReport>();
        final File file = new File(applicationPath + REPORT_PATH);

        if (file.isDirectory()) {
            final File[] pluginFiles = file.listFiles();

            for (int i = 0; i < pluginFiles.length; ++i) {
                if (!pluginFiles[i].isDirectory() && pluginFiles[i].getName().endsWith(".jar")) {
                    final ProjectTrackerReport ptr = getReportInstanceFromJar(pluginFiles[i]);
                    if (ptr != null) {
                        pluginsVector.add(ptr);
                        // initialises the report in an other thread
                        final Thread t = new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        ptr.init();
                                    }
                                });
                        t.start();
                    }
                }
            }
        }
        reports = pluginsVector.toArray(new ProjectTrackerReport[0]);
    }

    /**
     * get an instance of the plugin within the given jar-file.
     *
     * @param   pluginFile  a jar-file, that contains a plugin
     *
     * @return  an instance of the plugin or null, if the given file doesn't contain any plugin
     */
    private ProjectTrackerReport getReportInstanceFromJar(final File pluginFile) {
        try {
            final JarFile jarFile = new JarFile(pluginFile);
            final Enumeration<JarEntry> jarEntries = jarFile.entries();
            if (logger.isDebugEnabled()) {
                logger.debug("check report plugin: " + pluginFile.toURI().toURL());
            }

            while (jarEntries.hasMoreElements()) {
                final JarEntry jarEntry = jarEntries.nextElement();
                if (jarEntry.getName().startsWith(REPORT_PACKAGE.replace('.', '/'))
                            && jarEntry.getName().endsWith(".class")) {
                    String classname = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                    classname = classname.replace('/', '.');
                    try {
                        final Class cl = createClass(pluginFile, classname);
                        if (logger.isDebugEnabled()) {
                            logger.debug("load class with classloader: " + cl.getClassLoader().getClass().getName());
                        }
                        if ((cl.getSuperclass() != null)
                                    && cl.getSuperclass().getName().endsWith("ProjectTrackerReport")) {
                            return (ProjectTrackerReport)cl.newInstance(); // getConstructor(argsClass).newInstance(parameter);
                        }
                    } catch (Exception e) {
                        logger.error("can't find: " + jarEntry.getName(), e);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("can't find any report in jar: " + pluginFile.getName(), e);
        }

        return null;
    }

    /**
     * create a class object of the given class.
     *
     * @param   jarfile    jarfile object of the jarfile, that contains the class
     * @param   classname  name of the class, that class object should be returned
     *
     * @return  a class object of the given class
     *
     * @throws  MalformedURLException   DOCUMENT ME!
     * @throws  ClassNotFoundException  DOCUMENT ME!
     */
    private Class createClass(final File jarfile, final String classname) throws MalformedURLException,
        ClassNotFoundException {
        System.out.println("try to load " + classname + " jarfile " + jarfile);
        final URL[] u = new URL[1];
        u[0] = jarfile.toURI().toURL();
        if (urlc == null) {
            urlc = URLClassLoader.newInstance(u, ProjectTrackerReport.class.getClassLoader());
        } else {
            final URL[] urls = urlc.getURLs();
            boolean isContained = false;

            for (final URL tmp : urls) {
                if (tmp.toExternalForm().equals(u[0].toExternalForm())) {
                    isContained = true;
                }
            }

            if (!isContained) {
                urlc = URLClassLoader.newInstance(u, ProjectTrackerReport.class.getClassLoader());
            }
        }
        return urlc.loadClass(classname);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        for (final ProjectTrackerReport tmp : reports) {
            tmp.destroy();
        }
    }
}
