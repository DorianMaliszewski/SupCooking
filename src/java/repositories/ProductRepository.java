/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import models.Product;
/**
 *
 * @author MaliszewskiDorian
 */
public class ProductRepository {
    
    public static Product find(int id) {
        return new DefaultRepository<Product>(Product.class).find(id);
    }

    public static List<Product> findAll() {
        return new DefaultRepository<Product>(Product.class).findAll();
    }
    
    public static List<Product> findRange(Integer start, Integer end) {
        return new DefaultRepository<Product>(Product.class).findRange(new int[]{start,end});
    }
    
    public static boolean add(Product p) {
        try
        {
            new DefaultRepository<Product>(Product.class).create(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.ProductRepository.add() : Erreur lors de l'ajout : ");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean edit(Product p) {
        try
        {
            new DefaultRepository<Product>(Product.class).edit(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.ProductRepository.edit() : Erreur lors de la modification : ");
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean delete(Product p) {
        try
        {
            new DefaultRepository<Product>(Product.class).remove(p);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("repositories.ProductRepository.delete() : Erreur lors de la suppression : ");
            e.printStackTrace();
            return false;
        }
    }
    
    public static Integer count(){
        return new DefaultRepository<Product>(Product.class).count();
    }
}
