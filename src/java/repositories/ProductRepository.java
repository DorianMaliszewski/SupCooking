package repositories;

import java.util.List;
import models.Product;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import providers.HibernateUtil;
/**
 * DAO pour les produits
 * @author MaliszewskiDorian
 */
public class ProductRepository {
    
    /**
     * Retourne le produit correspondant à l'id passé en paramètre
     * @param id l'id du produit
     * @return le produit
     */
    public static Product find(int id) {
        return new DefaultRepository<Product>(Product.class).find(id);
    }

    /**
     * Retourne une liste de tous les produits
     * @return la liste de produits
     */
    public static List<Product> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
            List objs = s.createCriteria(Product.class).addOrder(Order.asc("name")).list();
        s.close();
        return objs;
    }
    
    /**
     * Retourne une liste de produit entre deux bornes des résultats de la requête
     * @param start le borne de début (commence à 0)
     * @param end la borne de fin
     * @return la liste de produit
     */
    public static List<Product> findRange(Integer start, Integer end) {
        return new DefaultRepository<Product>(Product.class).findRange(new int[]{start,end});
    }
    
    /**
     * Ajoute un produit dans la base de données
     * @param p le produit à ajouter
     * @return Si l'ajout à réussi ou non
     */
    public static boolean add(Product p) {
        return new DefaultRepository<Product>(Product.class).create(p) != null;
    }

    /**
     * Mets à jour le produit dans la base de donnée
     * @param p le produit à mettre à jour
     * @return Si la modification à réussi ou non
     */
    public static boolean edit(Product p) {
        return new DefaultRepository<Product>(Product.class).edit(p);
    }
    
    /**
     * Supprime un produit de la base de donnée
     * @param p le produit à supprimer
     * @return Si la suppression à réussi ou non
     */
    public static boolean delete(Product p) {
        return new DefaultRepository<Product>(Product.class).remove(p);
    }
    
    /**
     * Renvoi le nombre total de produit dans la base de données
     * @return Le nombre de produit
     */
    public static Integer count(){
        return new DefaultRepository<Product>(Product.class).count();
    }
}
