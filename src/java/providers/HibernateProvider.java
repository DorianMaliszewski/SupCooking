package providers;

import models.Recipe;
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
        Session s = factory.openSession();
        User u = new User();
        u.setUsername("admin");
        String[] pass = EncryptionProvider.encrypt("admin");
        u.setPassword(pass[1]);
        u.setSalt(pass[0]);
        u.setFirstName("Admin");
        u.setLastName("Admin");
        s.flush();
        s.close();
    }
    
    public static SessionFactory getFactory(){
        if(factory == null || factory.isClosed()){
            System.out.println("Initialize HibernateProvider");
            initialize();
        }
        return factory;
    }
}
