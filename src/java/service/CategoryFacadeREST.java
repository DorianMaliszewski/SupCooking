
package service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import models.Category;
import org.hibernate.Session;
import providers.HibernateUtil;

/**
 *Web service des catégories
 * @author Dorian Maliszewski
 */
@Path("categories")
public class CategoryFacadeREST extends AbstractFacade<Category> {

    private Session em = HibernateUtil.getSessionFactory().openSession();

    public CategoryFacadeREST() {
        super(Category.class);
    }

    /**
     * Récupère une catégorie avec l'id passé en paramètre sur une requête de type GET et retourne soit au format JSON soit XML (par défaut XML)
     * Pour récupérer au format JSON mettre Accept : application/json dans l'entête de la requête
     * @param id L'id à chercher
     * @return L'entité formattée dans le format choisi
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Category find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    /**
     * Récupère toutes les catégories sur une requête de type GET et retourne soit au format JSON soit XML (par défaut XML)
     * Pour récupérer au format JSON mettre Accept : application/json dans l'entête de la requête
     * @return La liste d'entité formattée dans le format choisi
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Category> findAll() {
        return super.findAll();
    }

    /**
     * Récupère une liste de catégorie d'une borne inférieure à une borne supérieure avec les bornes passées en paramètre sur une requête de type GET et retourne soit au format JSON soit XML (par défaut XML)
     * Pour récupérer au format JSON mettre Accept : application/json dans l'entête de la requête
     * @param from borne inférieure commançant à 0
     * @param to borne supérieure
     * @return La liste d'entité formattée dans le format choisi
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Category> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * Renvoi le nombre total d'entité dans la table
     * @return Le nombre total
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected Session getSession() {
        return em;
    }
    
}
