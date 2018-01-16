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
public class RecipeRepository extends AbstractRepository {
    public static Recipe find(int id){
        return (Recipe)AbstractRepository.find(Recipe.class, id);
    }
    
    public static List findAll(){
        return AbstractRepository.findAll(Recipe.class);
    }
   
}
