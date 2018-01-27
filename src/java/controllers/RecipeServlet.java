/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
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
import models.Product;
import models.Recipe;
import models.RecipeProduct;
import models.User;
import repositories.CategoryRepository;
import repositories.ProductRepository;
import repositories.RecipeRepository;

/**
 *
 * @author MaliszewskiDorian
 */
@WebServlet(name = "Recipe", urlPatterns = "/recipes/*")
@MultipartConfig
public class RecipeServlet extends HttpServlet {
    
    private final String VIEW_PATH = "/recipe/";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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

    private void indexByCategorie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("recipes", RecipeRepository.findAll());
        this.forward(request, response, "indexByCategory.jsp");
    }

    private void indexByRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.forward(request, response, "indexByRecipe.jsp");
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        if (req.getPathInfo() != null) {
            switch (req.getPathInfo()) {
                case "/categories":
                    indexByCategorie(req, resp);
                    break;
                case "/products":
                    indexByRecipe(req, resp);
                    break;
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

    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        Recipe recipe = RecipeRepository.find(Integer.parseInt(request.getParameter("id")));
        if (recipe == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        request.setAttribute("recipe", recipe);
        this.forward(request, response, "detail.jsp");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            Recipe recipe = new Recipe();
            System.out.println("controllers.RecipeServlet.add() POST");
            boolean success = true;
            try{
                success = RecipeRepository.add(fillRecipe(recipe, request));
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.err.println(e.getMessage());
                success = false;
            }
            String message = success ? "L'élément à bien été ajouté" : "Erreur lors de la création de l'élément";
            this.setFlashBag(message, success, request);
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        } else {
            request.setAttribute("categories", CategoryRepository.findAll());
            request.setAttribute("products", ProductRepository.findAll());
            this.forward(request, response, "form.jsp");
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        Recipe recipe = RecipeRepository.find(Integer.parseInt(request.getParameter("id")));
        if (request.getMethod().equals("POST") && recipe != null) {
            try {
                fillRecipe(recipe, request);
            } catch (Exception ex) {
                Logger.getLogger(RecipeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean success = RecipeRepository.edit(recipe);
            String message = success ? "Modification réussie" : "Erreur lors de l'enregistrement";
            this.setFlashBag(message, success, request);
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        } else {
            request.setAttribute("recipe", recipe);
            request.setAttribute("categories", CategoryRepository.findAll());
            request.setAttribute("products", ProductRepository.findAll());
            this.forward(request, response, "form.jsp");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        Recipe recipe = RecipeRepository.find(Integer.parseInt(request.getParameter("id")));
        if (request.getMethod().equals("POST") && recipe != null) {
            boolean success = RecipeRepository.delete(recipe);
            String message = success ? "Suppression réussie" : "Erreur lors de la suppresion";
            this.setFlashBag(message, success, request);
        }
        response.sendRedirect(request.getContextPath() + request.getServletPath());
    }
    
    private Recipe fillRecipe(Recipe recipe, HttpServletRequest request) throws NullPointerException, Exception{
        System.out.println("controllers.RecipeServlet.fillRecipe()");
        System.out.println("name : " + request.getParameter("name"));
        recipe.setName(request.getParameter("name"));
        recipe.setCreatedBy((User)request.getSession().getAttribute("user"));
        recipe.setDescription(request.getParameter("description"));
        System.out.println("description : " + request.getParameter("description"));
        recipe.setImage(request.getParameter("image"));
        recipe.setCookingTime(Integer.parseInt(request.getParameter("cookingTime")));
        recipe.setPreparationTime(Integer.parseInt(request.getParameter("preparationTime")));
        recipe.setCategory(CategoryRepository.find(Integer.parseInt(request.getParameter("category_id"))));
        int i = 0 ;
        recipe.getProducts().clear();
        while(request.getParameter("product_" + i + "[]") != null){
            String[] values = request.getParameterValues("product_" + i + "[]");
            recipe.addProduct(ProductRepository.find(Integer.valueOf(values[0])), Float.valueOf(values[1]), values[2]);
            i++;
        }
        return recipe;
    }
    
    private void setFlashBag(String message, Boolean success, HttpServletRequest request){
        request.getSession().setAttribute("success", success);
        request.getSession().setAttribute("message", message);
    }
    
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
