package service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import models.Recipe;
import org.hibernate.Session;
import providers.HibernateUtil;
import repositories.RecipeRepository;

/**
 * Web service des recettes
 * @author Dorian Maliszewski
 */
@Path("recipes")
public class RecipeFacadeREST extends AbstractFacade<Recipe> {

    private Session em = HibernateUtil.getSessionFactory().openSession();

    public RecipeFacadeREST() {
        super(Recipe.class);
    }

    /**
     * Récupère une recette avec l'id passé en paramètre sur une requête de type GET et retourne les données au format JSON
     * @param id L'id à chercher
     * @return L'entité formattée dans le format choisi
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Recipe find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    /**
     * Récupère toutes les recettes sur une requête de type GET et retourne les données au format JSON
     * @return La liste d'entité formattée dans le format choisi
     */
    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> findAll() {
        return super.findAll();
    }
    
    /**
     * Renvoie les recettes trouvées à partir de la chaîne de caractères passée en paramètre sur une requête de type GET et retourne les données au format JSON
     * @param s La chaîne de caractère
     * @return La liste formattée dans le format choisi
     */
    @GET
    @Path("search/{s}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> search(@PathParam("s") String s) {
        return RecipeRepository.findBySentences(s);
    }

    /**
     * Récupère une liste de recettes d'une borne inférieure à une borne supérieure avec les bornes passées en paramètre sur une requête de type GET et retourne les données au format JSON
     * @param from borne inférieure commançant à 0
     * @param to borne supérieure
     * @return La liste d'entité formattée dans le format choisi
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
