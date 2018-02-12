package repositories;

import java.util.List;
import models.Category;

/**
 * DAO pour les Catégories
 * @author MaliszewskiDorian
 */
public class CategoryRepository {

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
