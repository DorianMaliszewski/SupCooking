/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.Collection;
import java.util.List;
import models.Recipe;
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
    
    public static Object find(Class theClass, Integer id){
        Object obj = null;
        try {
            startOperation();
            obj = session.load(theClass, id);
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return obj;
    }
    
    public static List findAll(Class theClass){
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
    
    private static void startOperation(){
        session = HibernateProvider.getFactory().openSession();
        t = session.beginTransaction();        
    }
}
