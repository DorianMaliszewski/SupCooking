/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.Recipe;
import models.RecipeProduct;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import providers.HibernateUtil;

/**
 *
 * @author MaliszewskiDorian
 */
public class RecipeRepository {
    
   
    public static Recipe find(int id) {
        return new DefaultRepository<Recipe>(Recipe.class).find(id);
    }

    public static List<Recipe> findAll() {
        return new DefaultRepository<Recipe>(Recipe.class).findAll();
    }
    
    public static List<Recipe> findRange(Integer start, Integer end) {
        return new DefaultRepository<Recipe>(Recipe.class).findRange(new int[]{start,end});
    }
    
    public static boolean add(Recipe p) {
        try
        {
            Integer id = new DefaultRepository<>(Recipe.class).create(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.RecipeRepository.add() : Erreur lors de l'ajout : ");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean edit(Recipe p) {
        try
        {
            new DefaultRepository<>(Recipe.class).edit(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.RecipeRepository.edit() : Erreur lors de la modification : ");
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean delete(Recipe p) {
        try
        {
            new DefaultRepository<Recipe>(Recipe.class).remove(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.RecipeRepository.delete() : Erreur lors de la suppression : ");
            e.printStackTrace();
            return false;
        }
    }
    
    public static Integer count(){
        return new DefaultRepository<Recipe>(Recipe.class).count();
    }

    public static List<Recipe> findBySentences(String search) {
        
        List<Recipe> objs = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("SELECT r FROM Recipe r where UPPER(r.name) like UPPER('%" + search + "%') OR UPPER(r.description) like UPPER('%" + search + "%')");
            objs = q.list();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            if(session != null)
                session.close();
        }
        return objs;
    }
    
}
