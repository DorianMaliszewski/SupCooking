/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.Recipe;

/**
 *
 * @author MaliszewskiDorian
 */
public class RecipeRepository {
    
    /*public static List findAll(){
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
    }*/
   
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
            new DefaultRepository<Recipe>(Recipe.class).create(p);
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
            new DefaultRepository<Recipe>(Recipe.class).edit(p);
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
    
}
