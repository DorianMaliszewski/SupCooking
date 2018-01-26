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
public class CategoryRepository extends AbstractRepository {
    public static Category find(int id) {
        return (Category) AbstractRepository.find(Category.class, id);
    }

    public static List findAll() {
        List objs = null;
        Session session = HibernateProvider.getFactory().openSession();
        try {
            Transaction t = session.beginTransaction();
            objs = session.createQuery("SELECT c FROM Category c LEFT JOIN FETCH c.recipes").list();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return objs;
    }

    public static boolean add(Category category) {
        return AbstractRepository.add(category);
    }
    
    public static boolean delete(Category c){
        return AbstractRepository.delete(c);
    }
    
    public static boolean edit(Category c){
        return AbstractRepository.edit(c);
    }
}
