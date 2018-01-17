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
        return AbstractRepository.findAll(Category.class);
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
