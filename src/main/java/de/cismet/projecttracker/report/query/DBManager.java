/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.query;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.FileReader;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.List;
import java.util.Properties;

import javax.persistence.Table;

import de.cismet.projecttracker.report.exceptions.DataRetrievalException;

/**
 * This class provides some simple methods to access the database.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class DBManager {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger logger = Logger.getLogger(DBManager.class);

    //~ Instance fields --------------------------------------------------------

    protected Session hibernateSession;
    private final String CONF_BASE_DIR;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DBManager object.
     *
     * @param  confBaseDir  DOCUMENT ME!
     */
    public DBManager(final String confBaseDir) {
        CONF_BASE_DIR = confBaseDir;
        hibernateSession = HibernateUtil.getSession(confBaseDir);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * checks if the db manager has an open database session.
     *
     * @return  DOCUMENT ME!
     */
    public boolean isSessionOpen() {
        if (hibernateSession != null) {
            return hibernateSession.isOpen();
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Connection getDatabaseConnection() {
        try {
            final Properties dbConnProp = new Properties();
            final FileReader fr = new FileReader(CONF_BASE_DIR + System.getProperty("file.separator")
                            + "database.properties");
            dbConnProp.load(fr);

            Class.forName(dbConnProp.getProperty("driver"));
            return DriverManager.getConnection(dbConnProp.getProperty("path"),
                    dbConnProp.getProperty("user"),
                    dbConnProp.getProperty("password"));
        } catch (Exception e) {
            logger.error("Cannot open database connection.", e);
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cl  the class of the object that should be returned
     * @param   id  the id of the object that should be returned
     *
     * @return  the database object of the given class with the given id
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public Object getObject(final Class cl, final long id) throws DataRetrievalException {
        if (logger.isDebugEnabled()) {
            logger.debug("get object of type " + cl.getName() + " with id: " + id);
        }

        try {
            final Object result = hibernateSession.createCriteria(cl).add(Restrictions.eq("id", id)).uniqueResult();
            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   hqlQuery  the hql query that should be executed on the database
     *
     * @return  the results of the given query
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public Object getObject(final String hqlQuery) throws DataRetrievalException {
        if (logger.isDebugEnabled()) {
            logger.debug("get objects by attribute");
        }

        try {
            final Query query = hibernateSession.createQuery(hqlQuery);
            final Object result = query.uniqueResult();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cl  the class of the object that should be returned
     *
     * @return  the database object of the given class with the given id
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public List getAllObjects(final Class cl) throws DataRetrievalException {
        if (logger.isDebugEnabled()) {
            logger.debug("get all objects");
        }

        try {
            final List result = hibernateSession.createCriteria(cl).list();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * reads a object from the database.
     *
     * @param   cl         the class of the object that should be read
     * @param   attribute  the attribute, that should be select the object to read. This attribute should be unique
     *                     within the database.
     * @param   value      the object of the given class that has this value in the given attribute will be returned. If
     *                     this value matches more than one object, an exception will be thrown.
     *
     * @return  the database object object
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public Object getObjectByAttribute(final Class cl, final String attribute, final Object value)
            throws DataRetrievalException {
        if (logger.isDebugEnabled()) {
            logger.debug("get object by attribute");
        }

        try {
            final Object result = hibernateSession.createCriteria(cl)
                        .add(Restrictions.eq(attribute, value))
                        .setMaxResults(1)
                        .uniqueResult();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * reads all objects from the database, which fulfils the given restrictions.
     *
     * @param   cl         the class of the object that should be read
     * @param   attribute  the attribute, that should be select the object to read. This attribute should be unique
     *                     within the database.
     * @param   value      the object of the given class that has this value in the given attribute will be returned. If
     *                     this value matches more than one object, an exception will be thrown.
     *
     * @return  a list with all objects, which fulfils the given restrictions
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public List getObjectsByAttribute(final Class cl, final String attribute, final Object value)
            throws DataRetrievalException {
        if (logger.isDebugEnabled()) {
            logger.debug("get object by attribute");
        }

        try {
            final List result = hibernateSession.createCriteria(cl).add(Restrictions.eq(attribute, value)).list();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * reads any object of given class from the database.
     *
     * @param   cl  the class of the object that should be read
     *
     * @return  the database object
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public Object getAnyObjectByClass(final Class cl) throws DataRetrievalException {
        if (logger.isDebugEnabled()) {
            logger.debug("get object by attribute");
        }

        try {
            final Object result = hibernateSession.createCriteria(cl).setMaxResults(1).uniqueResult();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   hqlQuery  the hql query that should be executed on the database
     *
     * @return  a list, that contains the results of the given query
     *
     * @throws  DataRetrievalException  DOCUMENT ME!
     */
    public List getObjectsByAttribute(final String hqlQuery) throws DataRetrievalException {
        if (logger.isDebugEnabled()) {
            logger.debug("get objects by attribute");
        }

        try {
            final Query query = hibernateSession.createQuery(hqlQuery);
            final List result = query.list();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * Writes the given object to the database. The object should not contained in the database before.
     *
     * @param   object  the object that should be written to the database
     *
     * @return  the id of the given object within the database
     *
     * @throws  HibernateException  DOCUMENT ME!
     */
    public Serializable createObject(final Object object) throws HibernateException {
        Serializable res = null;
        Transaction ta = null;
        try {
            ta = hibernateSession.beginTransaction();
            res = hibernateSession.save(object);
            ta.commit();
        } catch (HibernateException t) {
            if (ta != null) {
                ta.rollback();
            }
            logger.error("Error", t);
            throw t;
        }

        return res;
    }

    /**
     * Writes the given object to the database. The object should already exists within the db.
     *
     * @param   object  the object that should be written to the database
     *
     * @throws  HibernateException  DOCUMENT ME!
     */
    public void saveObject(final Object object) throws HibernateException {
        Transaction ta = null;
        try {
            ta = hibernateSession.beginTransaction();
            hibernateSession.saveOrUpdate(object);
            ta.commit();
        } catch (HibernateException t) {
            if (ta != null) {
                ta.rollback();
            }
            logger.error("Error", t);
            throw t;
        }
    }

    /**
     * Deletes the given object from the db.
     *
     * @param   object  the object that should be deleted from the database
     *
     * @throws  HibernateException  DOCUMENT ME!
     */
    public void deleteObject(final Object object) throws HibernateException {
        Transaction ta = null;
        try {
            ta = hibernateSession.beginTransaction();
            hibernateSession.delete(object);
            ta.commit();
        } catch (PropertyValueException e) {
            // If such a missing property is in the db a NOT NULL property, then a PropertyValueException will be
            // thrown. Normally, such objects can be deleted with the method delete(String, Object)
            try {
                final Table an = object.getClass().getAnnotation(Table.class);
                if (an != null) {
                    hibernateSession.delete(an.name(), object);
                }
            } catch (HibernateException t) {
                logger.warn(e);
                if (ta != null) {
                    ta.rollback();
                }
                logger.error("Error", t);
                throw t;
            }
        } catch (HibernateException t) {
            if (ta != null) {
                ta.rollback();
            }
            logger.error("Error", t);
            throw t;
        }
    }

    /**
     * closes the hibernate session. The session was opened during the execution of the constructor
     */
    public void closeSession() {
        if (hibernateSession != null) {
            hibernateSession.close();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  a hibernate session
     */
    public Session getSession() {
        return hibernateSession;
    }
}
