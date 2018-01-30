/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.Recipe;
import models.RecipeProduct;

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
    
}
