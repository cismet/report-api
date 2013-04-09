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
package de.cismet.projecttracker.report.query;

import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class HibernateUtil {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   confBaseDir  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static SessionFactory createSessionfactory(final String confBaseDir) {
        try {
            if (logger.isDebugEnabled()) {
                // Create the SessionFactory from hibernate.cfg.xml
                logger.debug("create hibernate session factory");
            }
            final File hibernateConfFile = new File(confBaseDir + System.getProperty("file.separator")
                            + "ReportHibernate.cfg.xml");
            return new AnnotationConfiguration().configure(hibernateConfFile).buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            logger.error("Initial SessionFactory creation failed." + ex);
            logger.error("A database access is probably not possible");
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   confBaseDir  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Session getSession(final String confBaseDir) {
        if (sessionFactory == null) {
            sessionFactory = createSessionfactory(confBaseDir);
        }
        return sessionFactory.openSession();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   confBaseDir  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static SessionFactory getSessionFactory(final String confBaseDir) {
        if (sessionFactory == null) {
            sessionFactory = createSessionfactory(confBaseDir);
        }
        return sessionFactory;
    }
}
