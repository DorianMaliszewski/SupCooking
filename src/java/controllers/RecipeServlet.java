/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Product;
import models.Recipe;
import models.User;
import repositories.CategoryRepository;
import repositories.ProductRepository;
import repositories.RecipeRepository;

/**
 *
 * @author MaliszewskiDorian
 */
@WebServlet(name = "Recipe", urlPatterns = "/recipes/*")
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
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_PATH + "index.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        String url = "/recipe/indexByCategory.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void indexByRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("recipes", RecipeRepository.findAll());
        response.setContentType("text/html;charset=UTF-8");
        String url = "/recipe/indexByRecipe.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                    add(req, resp);
                    break;
                case "/edit":
                    edit(req, resp);
                    break;
                case "/delete":
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
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_PATH + "detail.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            response.setContentType("text/html;charset=UTF-8");
            Recipe recipe = new Recipe();
            boolean success = true;
            try{
            recipe.setName(request.getParameter("name"));
            recipe.setCreatedBy((User)request.getSession().getAttribute("user"));
            recipe.setDescription(request.getParameter("description"));
            recipe.setImage(request.getParameter("image"));
            recipe.setCookingTime(Integer.parseInt(request.getParameter("cookingTime")));
            recipe.setPreparationTime(Integer.parseInt(request.getParameter("preparationTime")));
            recipe.setCategory(CategoryRepository.find(Integer.parseInt(request.getParameter("category_id"))));
            for(Product p : (ArrayList<Product>)ProductRepository.findAll()){
                
            }
            success = RecipeRepository.add(recipe);
            }
            catch(Exception e)
            {
                System.err.println(e.getMessage());
                success = false;
            }
            String message = success ? "L'élément à bien été ajouté" : "Erreur lors de la création de l'élément";
            request.getSession().setAttribute("success", success);
            request.getSession().setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        } else {
            response.setContentType("text/html;charset=UTF-8");
            String url = VIEW_PATH + "/form.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        Recipe recipe = RecipeRepository.find(Integer.parseInt(request.getParameter("id")));
        if (request.getMethod().equals("POST")) {
            response.setContentType("text/html;charset=UTF-8");
            if (recipe == null) {
                recipe = new Recipe();
            }
            recipe.setName(request.getParameter("name"));
            boolean success = RecipeRepository.edit(recipe);
            String message = success ? "Modification réussie" : "Erreur lors de l'enregistrement";
            request.getSession().setAttribute("success", success);
            request.getSession().setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        } else {
            request.setAttribute("recipe", recipe);
            response.setContentType("text/html;charset=UTF-8");
            String url = VIEW_PATH + "form.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
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
            String message = success ? "Suppresion réussie" : "Erreur lors de la suppresion";
            request.getSession().setAttribute("success", success);
            request.getSession().setAttribute("message", message);

        }
        response.sendRedirect(request.getContextPath() + request.getServletPath());
    }
    
}
