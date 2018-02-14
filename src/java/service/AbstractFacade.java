package service;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

/**
 * Classe modèle des web services
 * @author Dorian Maliszewski
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract Session getSession();

    public void create(T entity) {
        getSession().persist(entity);
    }

    public void edit(T entity) {
        getSession().merge(entity);
    }

    public void remove(T entity) {
        getSession().delete(getSession().merge(entity));
    }

    public T find(Integer id) {
        return (T) getSession().get(entityClass, id);
    }

    public List<T> findAll() {
        return getSession().createQuery("from " + entityClass.getName()).list();
    }

    public List<T> findRange(int[] range) {
        Criteria c = getSession().createCriteria(entityClass.getName());
        c.setFirstResult(range[0]);
        c.setMaxResults(range[1] - range[0]);
        return c.list();
    }

    public int count() {        
        return ((Long)getSession().createCriteria(entityClass).setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }
    
}
