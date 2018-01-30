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
import models.Product;
import repositories.ProductRepository;

/**
 *
 * @author MaliszewskiDorian
 */
@WebServlet(name = "Product", urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {

    private final String VIEW_PATH = "/product/";
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
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_PATH + "index.jsp";
        request.setAttribute("products", ProductRepository.findAll());
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
        if(req.getSession().getAttribute("user") == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (req.getPathInfo() != null) {
            switch (req.getPathInfo()) {
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
        } 
        else
        {
            super.service(req, resp);
        }
    }
    
    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
            return;
        }
        Product product = ProductRepository.find(Integer.parseInt(request.getParameter("id")));
        if (product == null) {
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
            Product product = new Product();
            product.setName(request.getParameter("name"));
            boolean success = ProductRepository.add(product);
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
        Product product = ProductRepository.find(Integer.parseInt(request.getParameter("id")));
        if (request.getMethod().equals("POST")) {
            response.setContentType("text/html;charset=UTF-8");
            if (product == null) {
                product = new Product();
            }
            product.setName(request.getParameter("name"));
            boolean success = ProductRepository.edit(product);
            String message = success ? "Modification réussie" : "Erreur lors de l'enregistrement";
            request.getSession().setAttribute("success", success);
            request.getSession().setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        } else {
            request.setAttribute("product", product);
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
        Product product = ProductRepository.find(Integer.parseInt(request.getParameter("id")));
        if (request.getMethod().equals("POST") && product != null) {
            boolean success = ProductRepository.delete(product);
            String message = success ? "Suppresion réussie" : "Erreur lors de la suppresion";
            request.getSession().setAttribute("success", success);
            request.getSession().setAttribute("message", message);

        }
        response.sendRedirect(request.getContextPath() + request.getServletPath());
    }

    
}
