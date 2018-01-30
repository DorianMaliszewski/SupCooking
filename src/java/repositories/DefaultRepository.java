/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import providers.HibernateUtil;

/**
 *
 * @author MaliszewskiDorian
 */
public class DefaultRepository<T> {
    private static Session s;
    private Class<T> entityClass;

    public DefaultRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Session getSession(){ return s != null && s.isOpen() ? s : startSession(); };

    private Session startSession(){
        System.err.println("Create a session");
        s = HibernateUtil.getSessionFactory().openSession();
        return s;
    }
    public Integer create(T entity) {
        getSession().save(entity);
        getSession().flush();
        return (Integer)getSession().getIdentifier(entity);
    }

    public void edit(T entity) {
        getSession().saveOrUpdate(entity);
        getSession().flush();
    }

    public void remove(T entity) {
        getSession().delete(getSession().merge(entity));
        getSession().flush();
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