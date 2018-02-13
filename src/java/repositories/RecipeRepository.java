package repositories;

import java.util.List;
import models.Recipe;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import providers.HibernateUtil;

/**
 * DAO pour les Recettes
 * @author Maliszewski Dorian
 */
public class RecipeRepository {
    
   /**
     * Retourne la reccete correspondant à l'id passé en paramètre
     * @param id l'id de la recette
     * @return la recette
     */
    public static Recipe find(int id) {
        return new DefaultRepository<Recipe>(Recipe.class).find(id);
    }

    /**
     * Retourne une liste de toutes les recettes
     * @return la liste de recette
     */
    public static List<Recipe> findAll() {
        return new DefaultRepository<Recipe>(Recipe.class).findAll();
    }
    
    /**
     * Retourne une liste de recette entre deux bornes des résultats de la requête
     * @param start le borne de début (commence à 0)
     * @param end la borne de fin
     * @return la liste de recette
     */
    public static List<Recipe> findRange(Integer start, Integer end) {
        return new DefaultRepository<Recipe>(Recipe.class).findRange(new int[]{start,end});
    }
    
    /**
     * Ajoute une recette dans la base de données
     * @param p la recette à ajouter
     * @return Si l'ajout à réussi ou non
     */
    public static boolean add(Recipe p) {
        return new DefaultRepository<>(Recipe.class).create(p) != null;
    }

    /**
     * Mets à jour la recette dans la base de donnée
     * @param p la recette à mettre à jour
     * @return Si la modification à réussi ou non
     */
    public static boolean edit(Recipe p) {
        return new DefaultRepository<>(Recipe.class).edit(p);
    }
    
    /**
     * Supprime une recette de la base de donnée
     * @param p la recette à supprimer
     * @return Si la suppression à réussi ou non
     */
    public static boolean delete(Recipe p) {
        return new DefaultRepository<Recipe>(Recipe.class).remove(p);
    }
    
    /**
     * Renvoi le nombre total de recette dans la base de données
     * @return Le nombre de recette
     */
    public static Integer count(){
        return new DefaultRepository<Recipe>(Recipe.class).count();
    }

    /**
     * Renvoi une liste de recette contenant la chaôine de caractères dans leur nom ou description, ignore la casse
     * @param search la chaîne de caractères à rechercher
     * @return la liste de recette
     */
    public static List<Recipe> findBySentences(String search) {
        
        List<Recipe> objs = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("SELECT r FROM Recipe r where UPPER(r.name) like UPPER('%" + search + "%') OR UPPER(r.description) like UPPER('%" + search + "%')");
            objs = q.list();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            if(session != null)
                session.close();
        }
        return objs;
    }
    
}
