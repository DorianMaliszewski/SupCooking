package repositories;

import java.util.List;
import models.Category;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import providers.HibernateUtil;

/**
 * DAO pour les Catégories
 * @author MaliszewskiDorian
 */
public class CategoryRepository {

    /**
     * Retourne la catégorie correspondant à l'id passé en paramètre
     * @param id l'id de la catégorie
     * @return la catégorie
     */
    public static Category find(int id) {
        return new DefaultRepository<Category>(Category.class).find(id);
    }

    /**
     * Retourne une liste de toutes les catégories
     * @return la liste de catégorie
     */
    public static List<Category> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
            List objs = s.createCriteria(Category.class).addOrder(Order.asc("name")).list();
        s.close();
        return objs;
    }
    
    /**
     * Retourne une liste de catégorie entre deux bornes des résultats de la requête
     * @param start le borne de début (commence à 0)
     * @param end la borne de fin
     * @return la liste de catégorie
     */
    public static List<Category> findRange(Integer start, Integer end) {
        return new DefaultRepository<Category>(Category.class).findRange(new int[]{start,end});
    }
    
    /**
     * Ajoute une categorie dans la base de données
     * @param p la catégorie à ajouter
     * @return Si l'ajout à réussi ou non
     */
    public static boolean add(Category p) {
        return new DefaultRepository<Category>(Category.class).create(p) != null;
    }

    /**
     * Mets à jour la catégorie dans la base de donnée
     * @param p la catégorie à mettre à jour
     * @return Si la modification à réussi ou non
     */
    public static boolean edit(Category p) {
        return new DefaultRepository<Category>(Category.class).edit(p);
    }
    
    /**
     * Supprime une catégorie de la base de donnée
     * @param p la catégorie à supprimer
     * @return Si la suppression à réussi ou non
     */
    public static boolean delete(Category p) {
        return new DefaultRepository<Category>(Category.class).remove(p);
    }
    
    /**
     * Renvoi le nombre total de catégorie dans la base de données
     * @return Le nombre de catégorie
     */
    public static Integer count(){
        return new DefaultRepository<Category>(Category.class).count();
    }
        
}
