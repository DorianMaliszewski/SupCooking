/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import providers.HibernateProvider;
/**
 *
 * @author MaliszewskiDorian
 */
public class ProductRepository {
    
    public static Product find(int id) {
        Object obj = null;
        Session session = HibernateProvider.getFactory().openSession();
        try {
            Transaction t = session.beginTransaction();
            obj = session.get(Product.class, id);
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return (Product)obj;
    }

    public static List findAll() {
        List objs = null;
        Session session = HibernateProvider.getFactory().openSession();
        try {
            Transaction t = session.beginTransaction();
            objs = session.createQuery("SELECT p FROM Product p LEFT JOIN FETCH p.recipes").list();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return objs;
    }
    
    public static boolean add(Product p) {
        return AbstractRepository.add(p);
    }

    public static boolean delete(Product p) {
        return AbstractRepository.delete(p);
    }

    public static boolean edit(Product p) {
        return AbstractRepository.edit(p);
    }
}
