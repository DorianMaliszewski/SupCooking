/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

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
public class RecipeRepository extends AbstractRepository {
    public static Recipe find(int id){
        return (Recipe)AbstractRepository.find(Recipe.class, id);
    }
    
    public static List findAll(){
        List objs = null;
        Session session = HibernateProvider.getFactory().openSession();
        try {
            Transaction t = session.beginTransaction();
            objs = session.createQuery("SELECT r FROM Recipe r LEFT JOIN FETCH r.createdBy").list();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return objs;
    }
   
    public static boolean add(Recipe r) {
        return AbstractRepository.add(r);
    }

    public static boolean delete(Recipe r) {
        return AbstractRepository.delete(r);
    }

    public static boolean edit(Recipe r) {
        return AbstractRepository.edit(r);
    }
    
}
