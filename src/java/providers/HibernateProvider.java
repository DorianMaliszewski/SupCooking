package providers;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MaliszewskiDorian
 */
public class HibernateProvider {
    
    private static Session session = null;
    private static SessionFactory factory = null;
    
    private static void initialize(){
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        factory = cfg.buildSessionFactory(serviceRegistry);
    }
    
    public static SessionFactory getFactory(){
        if(factory == null || factory.isClosed()){
            System.out.println("Initialize HibernateProvider");
            initialize();
            System.out.println("HibernateProvider initialized");
        }
        return factory;
    }
}
