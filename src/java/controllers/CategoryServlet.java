/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.hibernate.jpa.HibernateEntityManager;
import providers.HibernateProvider;
import repositories.CategoryRepository;

/**
 *
 * @author MaliszewskiDorian
 */
@WebServlet(name = "CategoryServlet", urlPatterns = "/categories/*")
public class CategoryServlet extends HttpServlet {
    private final String VIEW_PATH = "/category/";
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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    
    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("id") == null){
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        Category category = CategoryRepository.find(Integer.parseInt(request.getParameter("id")));
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

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getMethod().equals("POST")){
            response.setContentType("text/html;charset=UTF-8");
            Category category = new Category();
            category.setName(request.getParameter("name"));
            boolean success = CategoryRepository.add(category);
            String message = success ? "L'élément à bien été ajouté" : "Erreur lors de la création de l'élément";
            request.getSession().setAttribute("success", success);
            request.getSession().setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        }
        else
        {
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
        Category category = CategoryRepository.find(Integer.parseInt(request.getParameter("id")));
        if (request.getMethod().equals("POST")) {
            response.setContentType("text/html;charset=UTF-8");
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
        Category category = CategoryRepository.find(Integer.parseInt(request.getParameter("id")));
        if (request.getMethod().equals("POST") && category != null) {
            boolean success = CategoryRepository.delete(category);
            String message = success ? "Suppresion réussie" : "Erreur lors de la suppresion";
            request.getSession().setAttribute("success", success);
            request.getSession().setAttribute("message", message);
            
        }
        response.sendRedirect(request.getContextPath() + request.getServletPath());
    }
    
    
}
