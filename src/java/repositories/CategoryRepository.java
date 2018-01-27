/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.Category;

/**
 *
 * @author MaliszewskiDorian
 */
public class CategoryRepository {
    
    /*public static List findAll() {
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
    }*/

    public static Category find(int id) {
        return new DefaultRepository<Category>(Category.class).find(id);
    }

    public static List<Category> findAll() {
        return new DefaultRepository<Category>(Category.class).findAll();
    }
    
    public static List<Category> findRange(Integer start, Integer end) {
        return new DefaultRepository<Category>(Category.class).findRange(new int[]{start,end});
    }
    
    public static boolean add(Category p) {
        try
        {
            new DefaultRepository<Category>(Category.class).create(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.CategoryRepository.add() : Erreur lors de l'ajout : ");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean edit(Category p) {
        try
        {
            new DefaultRepository<Category>(Category.class).edit(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.CategoryRepository.edit() : Erreur lors de la modification : ");
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean delete(Category p) {
        try
        {
            new DefaultRepository<Category>(Category.class).remove(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.CategoryRepository.delete() : Erreur lors de la suppression : ");
            e.printStackTrace();
            return false;
        }
    }
    
    public static Integer count(){
        return new DefaultRepository<Category>(Category.class).count();
    }
        
}
