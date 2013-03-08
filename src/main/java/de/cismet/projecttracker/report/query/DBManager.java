package de.cismet.projecttracker.report.query;

import de.cismet.projecttracker.report.exceptions.DataRetrievalException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.Table;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * This class provides some simple methods to access the database
 * @author therter
 */
public class DBManager {
    private static Logger logger = Logger.getLogger(DBManager.class);
    protected Session hibernateSession;


    public DBManager() {
        hibernateSession = HibernateUtil.getSession();
    }

    /**
     * checks if the db manager has an open database session.
     * @return
     */
    public boolean isSessionOpen() {
        if (hibernateSession != null) {
            return hibernateSession.isOpen();
        }

        return false;
    }

    
    public Connection getDatabaseConnection() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("de.cismet.projecttracker.report.query.database");
            Class.forName(bundle.getString("driver"));
            return DriverManager.getConnection(bundle.getString("path"), bundle.getString("user"), bundle.getString("password"));
        } catch(Exception e){
            logger.error("Cannot open database connection.", e);
        }
        
        return null;
    }    

    /**
     * @param cl the class of the object that should be returned
     * @param id the id of the object that should be returned
     * @return the database object of the given class with the given id
     * @throws DataRetrievalException
     */
    public Object getObject(Class cl, long id) throws DataRetrievalException {
        logger.debug("get object of type " + cl.getName() + " with id: " + id);

        try {
            Object result = hibernateSession.createCriteria(cl).
                                                     add( Restrictions.eq("id", id) ).uniqueResult();
            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }


    /**
     *
     * @param hqlQuery the hql query that should be executed on the database
     * @return the results of the given query
     * @throws DataRetrievalException
     */
    public Object getObject(String hqlQuery) throws DataRetrievalException {
        logger.debug("get objects by attribute" );

        try {
            Query query = hibernateSession.createQuery( hqlQuery );
            Object result = query.uniqueResult();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }


    /**
     * @param cl the class of the object that should be returned
     * @param id the id of the object that should be returned
     * @return the database object of the given class with the given id
     * @throws DataRetrievalException
     */
    public List getAllObjects(Class cl) throws DataRetrievalException {
        logger.debug("get all objects" );

        try {
            List result = hibernateSession.createCriteria(cl).list();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * reads a object from the database
     * @param cl the class of the object that should be read
     * @param attribute the attribute, that should be select the object to read. This attribute should be unique within the database.
     * @param value the object of the given class that has this value in the given attribute will be returned.
     *              If this value matches more than one object, an exception will be thrown.
     * @return the database object object
     * @throws DataRetrievalException
     */
    public Object getObjectByAttribute(Class cl, String attribute, Object value) throws DataRetrievalException {
        logger.debug("get object by attribute" );

        try {
            Object result = hibernateSession.createCriteria(cl).
                                                     add( Restrictions.eq(attribute, value) ).setMaxResults(1).uniqueResult();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * reads all objects from the database, which fulfils the given restrictions
     * @param cl the class of the object that should be read
     * @param attribute the attribute, that should be select the object to read. This attribute should be unique within the database.
     * @param value the object of the given class that has this value in the given attribute will be returned.
     *              If this value matches more than one object, an exception will be thrown.
     * @return a list with all objects, which fulfils the given restrictions
     * @throws DataRetrievalException
     */
    public List getObjectsByAttribute(Class cl, String attribute, Object value) throws DataRetrievalException {
        logger.debug("get object by attribute" );

        try {
            List result = hibernateSession.createCriteria(cl).
                                                     add( Restrictions.eq(attribute, value) ).list();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }


    /**
     * reads any object of given class from the database
     * @param cl the class of the object that should be read
     * @return the database object 
     * @throws DataRetrievalException
     */
    public Object getAnyObjectByClass(Class cl) throws DataRetrievalException {
        logger.debug("get object by attribute" );

        try {
            Object result = hibernateSession.createCriteria(cl).setMaxResults(1).uniqueResult();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     *
     * @param hqlQuery the hql query that should be executed on the database
     * @return a list, that contains the results of the given query
     * @throws DataRetrievalException
     */
    public List getObjectsByAttribute(String hqlQuery) throws DataRetrievalException {
        logger.debug("get objects by attribute" );

        try {
            Query query = hibernateSession.createQuery( hqlQuery );
            List result = query.list();

            return result;
        } catch (Throwable t) {
            logger.error("Error:", t);
            throw new DataRetrievalException(t);
        }
    }

    /**
     * Writes the given object to the database. The object should not contained in the
     * database before.
     *
     * @param object the object that should be written to the database
     * @return the id of the given object within the database
     * @throws HibernateException
     */
    public Serializable createObject(Object object) throws HibernateException {
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
     * @param object the object that should be written to the database
     * @throws HibernateException
     */
    public void saveObject(Object object) throws HibernateException {
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
     * Deletes the given object from the db
     *
     * @param object the object that should be deleted from the database
     * @throws HibernateException
     */
    public void deleteObject(Object object) throws HibernateException {
        Transaction ta = null;
        try {
            ta = hibernateSession.beginTransaction();
            hibernateSession.delete(object);
            ta.commit();
        } catch (PropertyValueException e) {
            // If such a missing property is in the db a NOT NULL property, then a PropertyValueException will be thrown.
            // Normally, such objects can be deleted with the method delete(String, Object)
            try {
                Table an =  object.getClass().getAnnotation(Table.class);
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
     * @return a hibernate session
     */
    public Session getSession() {
        return hibernateSession;
    }
}
