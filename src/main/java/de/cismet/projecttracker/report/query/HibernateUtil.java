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

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class HibernateUtil {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = getSessionfactory();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static SessionFactory getSessionfactory() {
        try {
            if (logger.isDebugEnabled()) {
                // Create the SessionFactory from hibernate.cfg.xml
                logger.debug("create hibernate session factory");
            }
            return new AnnotationConfiguration().configure("ReportHibernate.cfg.xml").buildSessionFactory();
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
     * @return  DOCUMENT ME!
     */
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
