package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import models.Product;
import models.Recipe;
import models.RecipeProduct;
import models.User;
import repositories.CategoryRepository;
import repositories.ProductRepository;
import repositories.RecipeRepository;
import repositories.UserRepository;

/**
 * Servlet pour gérer les requetes pour les recettes
 * @author Dorian Maliszewski
 */
@WebServlet(name = "Recipe", urlPatterns = "/recipes/*")
@MultipartConfig
public class RecipeServlet extends HttpServlet {
    
    private final String VIEW_PATH = "/recipe/";
    
    /**
     * Retourne vers la page index des recettes
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("recipes", RecipeRepository.findAll());
        this.forward(request, response, "index.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    /**
     * Joue le rôle de routeur
     * Pour les méthode d'édition, de suppresion et de création vérifie que l'utilisateur est bien connecté.
     * @param req La requête
     * @param resp La reponse
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //Si getPahtInfo retourne null alors l'url est la racine de mon servlet
        if (req.getPathInfo() != null) {
            switch (req.getPathInfo()) {
                case "/show":
                    show(req, resp);
                    break;
                case "/add":
                    if(req.getSession().getAttribute("user") == null){
                        resp.sendRedirect(req.getContextPath() + "/login");
                        return;
                    }
                    add(req, resp);
                    break;
                case "/edit":
                    if(req.getSession().getAttribute("user") == null){
                        resp.sendRedirect(req.getContextPath() + "/login");
                        return;
                    }
                    edit(req, resp);
                    break;
                case "/delete":
                    if(req.getSession().getAttribute("user") == null){
                        resp.sendRedirect(req.getContextPath() + "/login");
                        return;
                    }
                    delete(req, resp);
                    break;
                default:
                    super.service(req, resp);
                    break;
            }
        } else {
            super.service(req, resp);
        }
    }

    /**
     * Affiche le détail d'une recette
     * @param request La requête
     * @param response La reponse
     * @throws ServletException
     * @throws IOException 
     */
    protected void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //On vérifie qu'on à bien un id renseigné sinon on retourne à la page d'index des recettes
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        //On récupère la recette correspondant à l'id
        Recipe recipe = RecipeRepository.find(Integer.parseInt(request.getParameter("id")));
        
        //Si on a bien trouvé une recette sinon on retourne à la page d'index des recettes
        if (recipe == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        
        //Si on a bien une recette alors j'affiche ses détails et j'incrémente le nombre de vues que je sauvegarde en base
        recipe.setNumberOfView((recipe.getNumberOfView() != null ? recipe.getNumberOfView() : 0) + 1);
        RecipeRepository.edit(recipe);
        request.setAttribute("recipe", recipe);
        this.forward(request, response, "detail.jsp");
    }

    /**
     * Ajoute une recette pour l'utilisateur qui renseigne le formulair, renvoi sur la page d'index des recettes avec un message affiché pour savoir si l'élément à bien été crée
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On vérifie que ma requête est bien de type POST pour l'ajouter
        if (request.getMethod().equals("POST")) {
            Recipe recipe = new Recipe();
            boolean success = true;
            try{
                //On ajoute et récupère le booléen de la réussite de l'ajout
                success = RecipeRepository.add(fillRecipe(recipe, request));
                
                //On ajoute la recette a l'instance de l'utilisateur connecté.
                ((User)request.getSession().getAttribute("user")).getRecipes().add(recipe);
                
            }
            catch(Exception e)
            {
                e.printStackTrace();
                success = false;
            }
            
            //On mets le message pour informer l'utilisateur
            String message = success ? "L'élément à bien été ajouté" : "Erreur lors de la création de l'élément";
            this.setFlashBag(message, success, request);
            
            
            //Je renvoie vers la page d'index des recettes
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        }
        //Si la requête est de type GET j'affiche le formulaire
        else 
        {
            request.setAttribute("categories", CategoryRepository.findAll());
            request.setAttribute("products", ProductRepository.findAll());
            this.forward(request, response, "form.jsp");
        }
    }

    /**
     * Met à jour une recette et ses assocations. Redirige sur la page index, un message est affiché si l'élément à bien été mis à jour
     * @param request La requete http
     * @param response La reponse http
     * @throws IOException
     * @throws ServletException 
     */
    protected void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //On vérifie qu'il y a un id
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        
        Recipe recipe = RecipeRepository.find(Integer.parseInt(request.getParameter("id")));
        
        //Si la recette n'a pas été trouvée ou l'utilisateur de la recette n'est pas le même que l'utilisateur connecté
        if(
            recipe == null ||
            !Objects.equals(((User)request.getSession().getAttribute("user")).getId(), recipe.getCreatedBy().getId())
        ){
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        
        if (request.getMethod().equals("POST")) {
            try {
                //On remplit la recette
                fillRecipe(recipe, request);
            } catch (Exception ex) {
                Logger.getLogger(RecipeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            boolean success = RecipeRepository.edit(recipe);
            String message = success ? "Modification réussie" : "Erreur lors de l'enregistrement";
            this.setFlashBag(message, success, request);
            
            //On mets à jour l'objet User pour avoir les mises à jour
            request.getSession().setAttribute("user", UserRepository.find(((User)request.getSession().getAttribute("user")).getId()));
            
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        } 
        //Sinon on renvoit sur le formulaire
        else
        {
            request.setAttribute("recipe", recipe);
            request.setAttribute("categories", CategoryRepository.findAll());
            request.setAttribute("products", ProductRepository.findAll());
            this.forward(request, response, "form.jsp");
        }
    }

    /**
     * Supprime une recette
     * @param request La requete http avec un id en paramètre
     * @param response La reponse http
     * @throws IOException
     * @throws ServletException
     */
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        Recipe recipe = RecipeRepository.find(Integer.parseInt(request.getParameter("id")));
        
        //Si la recette n'a pas été trouvée ou l'utilisateur de la recette n'est pas le même que l'utilisateur connecté
        if(
            recipe == null ||
            !Objects.equals(((User)request.getSession().getAttribute("user")).getId(), recipe.getCreatedBy().getId())
        ){
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        
        if (request.getMethod().equals("POST")) {
            boolean success = RecipeRepository.delete(recipe);
            
            //On mets à jour l'objet User pour avoir les mises à jour
            request.getSession().setAttribute("user", UserRepository.find(((User)request.getSession().getAttribute("user")).getId()));
            
            String message = success ? "Suppression réussie" : "Erreur lors de la suppresion";
            this.setFlashBag(message, success, request);
        }
        response.sendRedirect(request.getContextPath() + request.getServletPath());
    }
    
    /**
     * Remplit la recette et ses association puis retourne la recette
     * @param recipe L'instance de recette à remplir
     * @param request La requete contenant les paramètres
     * @return La recette remplie
     * @throws NullPointerException Si un paramètre n'est pas renseigné retourne une erreur
     * @throws Exception Retourne une exception si un même produit plusieurs fois
     */
    protected Recipe fillRecipe(Recipe recipe, HttpServletRequest request) throws NullPointerException, Exception{
        System.out.println("controllers.RecipeServlet.fillRecipe()");
        
        //On remplit les paramètre de la recette
        
        recipe.setName(request.getParameter("name"));
        
        recipe.setCreatedBy((User)request.getSession().getAttribute("user"));
        
        //La description est enregistrée avec des \n pour qu'elle s'affiche bien dans la textarea
        recipe.setDescription(request.getParameter("description"));
        
        //Si on a pas uploader d'image alors on la mets pas à jour
        if (request.getPart("image").getSize() != 0)
            //Télécharge l'image et mets l'url dans la propriété
            recipe.setImage(getBaseUrl(request) + downloadImage(request.getPart("image")));
        
        recipe.setDifficulty(Integer.parseInt(request.getParameter("difficulty")));
        recipe.setCookingTime(Integer.parseInt(request.getParameter("cookingTime")));
        recipe.setPreparationTime(Integer.parseInt(request.getParameter("preparationTime")));
        
        recipe.setCategory(CategoryRepository.find(Integer.parseInt(request.getParameter("category_id"))));
  
        int i = 0 ;
        
        //On boucle sur les produits du formulaire
        while(request.getParameter("product_" + i + "[]") != null){
            String[] values = request.getParameterValues("product_" + i + "[]");
            Integer idProduit = null;
            try{
                idProduit = Integer.valueOf(values[0]);
            }catch(NumberFormatException e){
                i++;
                continue;
            }
            
            Product p = ProductRepository.find(idProduit);
            
            //On cherche dans la collection si un produit existe déjà
            Optional<RecipeProduct> rp = recipe.getProducts().stream().filter(recipeProduct -> (recipeProduct.getProduct() == p)).findFirst();
            
            //Si il existe plusieurs fois le même produit alors on retourne une erreur
            if(recipe.getProducts().stream().filter(recipeProduct -> (recipeProduct.getProduct() == p)).count() > 1){
                setFlashBag("Redondance de produit interdite", false, request);
                throw new Exception("Plusieurs fois le même produit");
            }
            
            //On verifie si on a bien trouve un produit dans ce cas on met à jour l'association autrement on ajoute une association
            if (rp.isPresent()) {
                rp.get().setQuantity(Float.valueOf(values[1]));
                rp.get().setUnit(values[2]);
            } else {
                recipe.addProduct(p, Float.valueOf(values[1]), values[2]);
            }
            i++;
        }
        return recipe;
    }
    
    /**
     * Paramètre un message sur la vue retournée, écrase le message précédent si il y en a un
     * @param message Le texte à afficher
     * @param success Si true message en vert sinon en rouge
     * @param request La requete sur laquelle paramétrer le message
     */
    protected void setFlashBag(String message, Boolean success, HttpServletRequest request){
        request.getSession().setAttribute("success", success);
        request.getSession().setAttribute("message", message);
    }
    
    /**
     * Retourne la reponse vers une vue
     * @param request La requete a retournée
     * @param response La reponse a retournée
     * @param view La vue que l'on souhaite afficher
     * @throws ServletException
     * @throws IOException 
     */
    protected void forward(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_PATH + view;
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
        request.getSession().setAttribute("message", null);
        request.getSession().setAttribute("success", null);
    }
    
    
    /**
     * A partir du part passé en paramètre télécharge l'image et renvoi l'url pour y accéder
     * @param filePart le part de l'image
     * @return l'url de l'image
     * @throws IOException 
     */
    protected String downloadImage(Part filePart) throws IOException{
        
        //Streams
        OutputStream out = null;
        InputStream inputStream = null;
        
        String fileName = null;
        
        // On récupère le stream de l'image
        inputStream = filePart.getInputStream();
        
        //On recupère son nom splitter
        String[] array = filePart.getSubmittedFileName().split("\\.");
        
        //On créer le nom du fichier
        fileName = getRandomString() + "." + array[(array.length - 1)];
        
        //On copie le fichier
        try {
            out = new FileOutputStream(new File( getServletContext().getRealPath(File.separator) + "upload/" + fileName));

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        
        } catch (FileNotFoundException fne) {
                System.out.println("controllers.RecipeServlet.fillRecipe() : error : " + fne.getMessage());
        } finally {
            //On ferme les streams
            if (out != null) {
                out.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return "/upload/" + fileName;
    }
    
    
    /**
     * Retourne une chaîne de caractères aléatoire
     * @return La chaîne de caractères
     */
    protected String getRandomString() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // Longueur de la chaîne de caractère voulu
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    
    /**
     * Retourne l'url de base du serveur
     * exemple : http://localhost:8080/SupCooking/
     * @param request
     * @return La string de l'url
     */
    protected String getBaseUrl( HttpServletRequest request ) {
    if ( ( request.getServerPort() == 80 ) ||
         ( request.getServerPort() == 443 ) )
        return  request.getScheme() + "://" +
                request.getServerName() +
                request.getContextPath();
    else
        return  request.getScheme() + "://" +
                request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath();
  }
  
}
