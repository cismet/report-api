/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report.query;

import java.io.File;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author therter
 */
public class HibernateUtil {
    private static Logger logger = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    private static SessionFactory getSessionfactory(String confBaseDir) {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            logger.debug("create hibernate session factory");
            final File hibernateConfFile = new File(confBaseDir+System.getProperty("file.seperator")+"ReportHibernate.cfg.xml");
            return new AnnotationConfiguration().configure(hibernateConfFile).buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            logger.error("Initial SessionFactory creation failed." + ex);
            logger.error("A database access is probably not possible");
        }

        return null;
    }

    public static Session getSession(String confBaseDir) {
        if(sessionFactory== null){
            sessionFactory = getSessionFactory(confBaseDir);
        }
        return sessionFactory.openSession();
    }

    public static SessionFactory getSessionFactory(String confBaseDir) {
        if(sessionFactory == null){
            sessionFactory = getSessionFactory(confBaseDir);
        }
        return sessionFactory;
    }
}
