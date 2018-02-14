package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Classe de configuration des web services
 * @author Dorian Maliszewski
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.CategoryFacadeREST.class);
        resources.add(service.ProductFacadeREST.class);
        resources.add(service.RecipeFacadeREST.class);
    }
    
}
