/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.Category;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import providers.HibernateProvider;

/**
 *
 * @author MaliszewskiDorian
 */
abstract class AbstractRepository {
    private static Session session;
    private static Transaction t;
    
    protected static Object find(Class theClass, Integer id){
        Object obj = null;
        try {
            startOperation();
            obj = session.get(theClass, id);
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return obj;
    }
    
    protected static List findAll(Class theClass){
        List objs = null;
        try {
            startOperation();
            objs = session.createQuery("from " + theClass.getName()).list();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return objs;
    }
    
    protected static boolean add(Object o){
        boolean success = true;
        try {
            startOperation();
            session.saveOrUpdate(o);
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            success = !success;
        } finally {
            session.close();        
        }
        return success;
    }
    
    protected static void startOperation(){
        session = HibernateProvider.getFactory().openSession();
        t = session.beginTransaction();
    }

    protected static boolean delete(Object o) {
        boolean success = true;
        try {
            startOperation();
            session.delete(o);
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            success = !success;
        } finally {
            session.close();
        }
        return success;
    }

    static boolean edit(Object o) {
        boolean success = true;
        try {
            startOperation();
            session.merge(o);
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            success = !success;
        } finally {
            session.close();
        }
        return success;
    }
}
