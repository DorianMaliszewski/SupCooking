package providers;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author dorian
 */
public class HibernateUtil {

    /**
     * La session factory
     */
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    /**
     * Renvoi la sessionFactory paramétrer
     * @return La session factory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    
}
