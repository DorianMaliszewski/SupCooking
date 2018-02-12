package service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import models.Product;
import org.hibernate.Session;
import providers.HibernateUtil;

/**
 *Web service des produits
 * @author Dorian Maliszewski
 */
@Path("products")
public class ProductFacadeREST extends AbstractFacade<Product> {
    
    private Session em = HibernateUtil.getSessionFactory().openSession();

    public ProductFacadeREST() {
        super(Product.class);
    }
    
    /**
     * Récupère un produit avec l'id passé en paramètre sur une requête de type GET et retourne soit au format JSON soit XML (par défaut XML)
     * Pour récupérer au format JSON mettre Accept : application/json dans l'entête de la requête
     * @param id L'id à chercher
     * @return L'entité formattée dans le format choisi
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Product find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    /**
     * Récupère tous le sproduits sur une requête de type GET et retourne soit au format JSON soit XML (par défaut XML)
     * Pour récupérer au format JSON mettre Accept : application/json dans l'entête de la requête
     * @return La liste d'entité formattée dans le format choisi
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findAll() {
        return super.findAll();
    }

    /**
     * Récupère une liste de produit d'une borne inférieure à une borne supérieure avec les bornes passées en paramètre sur une requête de type GET et retourne soit au format JSON soit XML (par défaut XML)
     * Pour récupérer au format JSON mettre Accept : application/json dans l'entête de la requête
     * @param from borne inférieure commançant à 0
     * @param to borne supérieure
     * @return La liste d'entité formattée dans le format choisi
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
