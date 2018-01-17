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
        return (Product) AbstractRepository.find(Product.class, id);
    }

    public static List findAll() {
        return AbstractRepository.findAll(Product.class);
    }
    
    public static boolean add(Product p) {
        return AbstractRepository.add(p);
    }

    public static boolean delete(Product p) {
        return AbstractRepository.delete(p);
    }

    public static boolean edit(Product p) {
        return AbstractRepository.edit(p);
    }
}
