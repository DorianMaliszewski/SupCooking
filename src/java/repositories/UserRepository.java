package repositories;

import java.util.List;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import providers.HibernateUtil;

/**
 * Dao pour les utilisateurs
 * @author Maliszewski Dorian
 */
public class UserRepository {

    /**
     * Retourne l'utilisateur correspondant à l'id passé en paramètre
     * @param id l'id de l'utilisateur
     * @return l'utilisateur
     */
    public static User find(int id) {
        return new DefaultRepository<User>(User.class).find(id);
    }

    /**
     * Retourne une liste de tous les utilisateurs
     * @return la liste d'utilisateur
     */
    public static List<User> findAll() {
        return new DefaultRepository<User>(User.class).findAll();
    }
    
    /**
     * Retourne une liste d'utilisateur entre deux bornes des résultats de la requête
     * @param start le borne de début (commence à 0)
     * @param end la borne de fin
     * @return la liste d'utilisateur
     */
    public static List<User> findRange(Integer start, Integer end) {
        return new DefaultRepository<User>(User.class).findRange(new int[]{start,end});
    }
    
    /**
     * Ajoute un utilisateur dans la base de données
     * @param p l'utilisateur à ajouter
     * @return Si l'ajout à réussi ou non
     */
    public static boolean add(User p) {
        return new DefaultRepository<User>(User.class).create(p) != null;
    }

    /**
     * Mets à jour l'utilisateur dans la base de donnée
     * @param p l'utilisateur à mettre à jour
     * @return Si la modification à réussi ou non
     */
    public static boolean edit(User p) {
        return new DefaultRepository<User>(User.class).edit(p);
    }
    
    /**
     * Supprime un utilisateur de la base de donnée
     * @param p l'utilisateur à supprimer
     * @return Si la suppression à réussi ou non
     */
    public static boolean delete(User p) {
        return new DefaultRepository<User>(User.class).remove(p);
    }
    
    /**
     * Renvoi le premier utilisateur du résultat de la requête retournant les utilisateurs selon l'identifiant passé en paramètre
     * @param username l'identifiant pour la recherche
     * @return l'utilisateur trouvé
     */
    public static User findByUsername(String username) {
        
        Object obj = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.recipes where username = :username");
            q.setString("username", username);
            obj = q.uniqueResult();
            t.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        } finally {
            if(session != null)
                session.close();
        }
        return (User)obj;
    }
    
    /**
     * Renvoi le nombre total d'utilisateur dans la base de données
     * @return Le nombre d'utilisateur
     */
    public static Integer count(){
        return new DefaultRepository<User>(User.class).count();
    }
}
