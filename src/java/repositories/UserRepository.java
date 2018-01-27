/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import providers.HibernateUtil;

/**
 *
 * @author MaliszewskiDorian
 */
public class UserRepository {
    /*public static User find(int id) {
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
    }*/

    public static User find(int id) {
        return new DefaultRepository<User>(User.class).find(id);
    }

    public static List<User> findAll() {
        return new DefaultRepository<User>(User.class).findAll();
    }
    
    public static List<User> findRange(Integer start, Integer end) {
        return new DefaultRepository<User>(User.class).findRange(new int[]{start,end});
    }
    
    public static boolean add(User p) {
        try
        {
            new DefaultRepository<User>(User.class).create(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.UserRepository.add() : Erreur lors de l'ajout : ");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean edit(User p) {
        try
        {
            new DefaultRepository<User>(User.class).edit(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.UserRepository.edit() : Erreur lors de la modification : ");
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean delete(User p) {
        try
        {
            new DefaultRepository<User>(User.class).remove(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.UserRepository.delete() : Erreur lors de la suppression : ");
            e.printStackTrace();
            return false;
        }
    }
    
    public static User findByUsername(String username) {
        
        Object obj = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.recipes where username = :username");
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
    
    public static Integer count(){
        return new DefaultRepository<User>(User.class).count();
    }
}
