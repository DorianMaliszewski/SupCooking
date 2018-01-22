/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import providers.HibernateProvider;

/**
 *
 * @author MaliszewskiDorian
 */
public class UserRepository {
    public static User find(int id) {
        List objs = null;
        Session session = HibernateProvider.getFactory().openSession();
        try {
            Transaction t = session.beginTransaction();
            objs = session.createQuery("SELECT u FROM Users u LEFT JOIN FETCH u.recipe").list();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return (User)objs;
    }

    public static List findAll() {
        return AbstractRepository.findAll(User.class);
    }
    
    public static boolean add(User u) {
        return AbstractRepository.add(u);
    }

    public static boolean delete(User u) {
        return AbstractRepository.delete(u);
    }

    public static boolean edit(User u) {
        return AbstractRepository.edit(u);
    }

    public static User findByUsername(String username) {
        
        Object obj = null;
        Session session = null;
        try {
            session = HibernateProvider.getFactory().openSession();
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("from User where username = :username");
            q.setString("username", username);
            obj = q.uniqueResult();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            if(session != null)
                session.close();
        }
        return (User)obj;
    }
}
