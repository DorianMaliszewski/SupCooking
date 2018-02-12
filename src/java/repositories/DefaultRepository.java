package repositories;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import providers.HibernateUtil;

/**
 * DAO Modèle dont on se sert pour accéder à la base de données
 * @author MaliszewskiDorian
 */
public class DefaultRepository<T> {
    private static Session s;
    private Class<T> entityClass;

    /**
     * Initialise les méthode avec la classe Entité passée en paramètre
     * @param entityClass la classe de l'entité à utiliser
     */
    public DefaultRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Retourne la session propriété statique de la classe si elle n'est pas ouverte ouvre une nouvelle session.
     * @return 
     */
    protected Session getSession(){ return s != null && s.isOpen() ? s : startSession(); };

    /**
     * Renvoi une session, la session courante si une est déjà ouvert sinon ouvre une nouvelle session
     * @return Session la session
     */
    private Session startSession(){
        System.err.println("Create a session");
        s = HibernateUtil.getSessionFactory().getCurrentSession().isOpen() ? 
                HibernateUtil.getSessionFactory().getCurrentSession(): 
                HibernateUtil.getSessionFactory().openSession();
        return s;
    }
    
    /**
     * Permet d'ajouter une nouvelle entité
     * @param entity l'entité à ajouter
     * @return L'id de l'entité ajoutée
     */
    public Integer create(T entity) {
        getSession().save(entity);
        getSession().flush();
        return (Integer)getSession().getIdentifier(entity);
    }

    /**
     * Edite une entité
     * @param entity L'entité à ajouter
     */
    public void edit(T entity) {
        getSession().saveOrUpdate(entity);
        getSession().flush();
    }

    /**
     * Supprime une entité
     * @param entity l'entité à supprimer
     */
    public void remove(T entity) {
        getSession().delete(getSession().merge(entity));
        getSession().flush();
    }

    /**
     * Retourne une entité en fonction de son id passé en paramètre et la classe précisée à l'initialisation
     * @param id L'id de l'entité
     * @return L'entité
     */
    public T find(Integer id) {
        return (T) getSession().get(entityClass, id);
    }

    /**
     * Retourne une liste d'entité de la classe utilisée durant l'initialisation
     * @return La liste d'entités
     */
    public List<T> findAll() {
        return getSession().createQuery("from " + entityClass.getName()).list();
    }

    /**
     * Retourne une liste d'entité de la taille de la range passé en paramètre en triant l'id de façon décroissante
     * @param range Tableau avec deux nombres start (début 0),end
     * @return La liste d'entité
     */
    public List<T> findRange(int[] range) {
        Criteria c = getSession().createCriteria(entityClass.getName());
        c.addOrder(Order.desc("id"));
        c.setFirstResult(range[0]);
        c.setMaxResults(range[1] - range[0]);
        return c.list();
    }

    /**
     * Retourne le nombre total d'éléement présent dans la table correspondant à l'entité
     * @return 
     */
    public int count() {        
        return ((Long)getSession().createCriteria(entityClass).setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }
}
