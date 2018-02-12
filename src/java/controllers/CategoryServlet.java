package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Category;
import repositories.CategoryRepository;

/**
 * Servlet pour gérer les requêtes http concernant les catégories
 * @author Dorian Maliszewski
 */
@WebServlet(name = "CategoryServlet", urlPatterns = "/categories/*")
public class CategoryServlet extends HttpServlet {
    
    //La racine où sont stocké les vues
    private final String VIEW_PATH = "/category/";
    
    /**
     * Retourne la page d'index des categories et après l'avoir retrouné, vide la session du message que l'on a affiché
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CategoryController");
        request.setAttribute("categories", CategoryRepository.findAll());
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_PATH + "index.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
        request.getSession().setAttribute("message", null);
        request.getSession().setAttribute("success", null);
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
        //Si l'utilisateur n'est pas connecté je le renvoi vers la page de login
        if(req.getSession().getAttribute("user") == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (req.getPathInfo() != null) {
            switch (req.getPathInfo()) {
                case "/show":
                    show(req,resp);
                    break;
                case "/add":
                    add(req,resp);
                    break;
                case "/edit":
                    edit(req,resp);
                    break;
                case "/delete":
                    delete(req,resp);
                    break;
                default:
                    super.service(req, resp);
                    break;
            }
        } 
        else
        {
            super.service(req, resp);
        }
    }
    
    /**
     * Affiche le détail d'une catégorie ou renvoi vers l'index des catégories si aucun id n'est renseigné dans la requête
     * @param request La requete
     * @param response La reponse
     * @throws ServletException
     * @throws IOException 
     */
    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On vérifie qu'un id est bien renseigné si je renvoi à la page d'index des catégories
        if(request.getParameter("id") == null){
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        
        //Je récupère la catégorie
        Category category = CategoryRepository.find(Integer.parseInt(request.getParameter("id")));
        
        //Si aucune catégorie n'a été trouvée alors je renvoi vers la page d'index des catégories
        if(category == null){
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_PATH + "detail.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
        
    }

    /**
     * Ajoute une catégorie avec les paramètre renseigné.
     * GET : Retourne la formulaire d'ajout de catégorie
     * POST : Ajoute la catégorie avec les paramètres renseigné puis retourne à la page d'index des catégories
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getMethod().equals("POST")){
            
            Category category = new Category();
            if(request.getParameter("name") == null){
                this.setFlashBag("Il faut renseigné un nom", false, request);
                this.forward(request, response, "form.jsp");
                return;
            }
            
            category.setName(request.getParameter("name"));
            
            //On ajoute la catégorie et on récupère le résultat
            boolean success = CategoryRepository.add(category);
            
            //On informe l'utilisateur du résultat de l'ajout et on redirige vers l'index des catégories
            String message = success ? "L'élément à bien été ajouté" : "Erreur lors de la création de l'élément";
            this.setFlashBag(message, success, request);
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        }
        else
        {
           this.forward(request, response, "form.jsp");
        }
    }

    /**
     * Modifie une catégorie avec les informations passé en paramètre.
     * Si la requête est de type GET affiche le formulaire.
     * Si elle est de type POST modifie la catégorie et retourne vers la page d'index des catégories
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        Category category = CategoryRepository.find(Integer.parseInt(request.getParameter("id")));
        if (request.getMethod().equals("POST")) {
            if(category == null)
            {
               category = new Category();
            }
            category.setName(request.getParameter("name"));
            boolean success = CategoryRepository.edit(category);
            String message = success ? "Modification réussie" : "Erreur lors de l'enregistrement";
            request.getSession().setAttribute("success", success);
            request.getSession().setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        } else {
            request.setAttribute("category", category);
            this.forward(request, response, "form.jsp");
        }
    }

    /**
     * Supprime la catégorie avec l'id passé en paramètre, type post
     * @param request La requete
     * @param response La repsonse
     * @throws IOException
     * @throws ServletException 
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        //On vérifie qu'il y a bine un id sinon on retourne vers la page d'index des catégories
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        
        //On récupère la catégorie
        Category category = CategoryRepository.find(Integer.parseInt(request.getParameter("id")));
        
        //On vérifie que ma requête est bien de type POST et qu'une catégorie a été trouvée
        if (request.getMethod().equals("POST") && category != null) {
            boolean success = CategoryRepository.delete(category);
            String message = success ? "Suppresion réussie" : "Erreur lors de la suppresion";
            this.setFlashBag(message, success, request);
            
        }
        response.sendRedirect(request.getContextPath() + request.getServletPath());
    }
    
    /**
     * Paramètre un message sur la vue retournée, écrase le message précédent si il y en a un
     * @param message Le texte à afficher
     * @param success Si true message en vert sinon en rouge
     * @param request La requete sur laquelle paramétrer le message
     */
    private void setFlashBag(String message, Boolean success, HttpServletRequest request){
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
    private void forward(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_PATH + view;
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
        request.getSession().setAttribute("message", null);
        request.getSession().setAttribute("success", null);
    }
}
