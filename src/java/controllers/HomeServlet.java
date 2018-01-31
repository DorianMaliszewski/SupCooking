/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import repositories.RecipeRepository;
import repositories.UserRepository;
/**
 *
 * @author MaliszewskiDorian
 */
@WebServlet(name = "Home", urlPatterns = "/")
public class HomeServlet extends HttpServlet {

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
        System.out.println("HomeController");
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("recipesNumber", RecipeRepository.count());
        request.setAttribute("usersNumber", UserRepository.count());
        request.setAttribute("recipes", RecipeRepository.findRange(0,5));
        String url = "/index.jsp";
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
     * @param req La requête
     * @param resp La reponse
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        if(req.getServletPath().equals("/search"))
        {
            this.search(req,resp);
        }
        else if(req.getServletPath().equals("/addMarkedRecipe"))
        {
            this.addMarked(req,resp);
        }
        else
        {
            super.service(req, resp);
        }
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException{
        
        if(request.getParameter("s") == null){
            response.sendRedirect(request.getContextPath());
            request.getSession().setAttribute("message", "Renseingez un text de recherche");
            request.getSession().setAttribute("success", false);
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        String url = "/search.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void addMarked(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=utf-8");
        
        PrintWriter p = resp.getWriter();
        Cookie c = new Cookie("test", "test");
        c.setMaxAge(120);
        resp.addCookie(c);
        Cookie[] cookies = req.getCookies();
        Boolean exist = false;
        int id;
        
        //On verifie de bien avoir un id en paramètre sinon on retourne une erreur
        if(req.getParameter("id") == null)
        {
            //resp.sendError(400, "Aucun id de recette renseigné");
            p.println("Aucun id de recette renseigné");
            return;
        }
        //On enregistre notre id (Si ce n'est pas un nombre une erreur sera levée)
        else
        {
            id = Integer.valueOf(req.getParameter("id"));
        }
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("recipesMarked")) {
                    exist = true;
                    cookie.setMaxAge(60*60*24*30);
                    cookie.setValue(cookie.getValue() + ";" + id);
                }
            }
        }
        /**if(!exist){
            Cookie c = new Cookie("recipesMarked", String.valueOf(id));
            c.setComment("Id des recettes mises en favoris");
            c.setDomain("SupCooking");
            c.setSecure(false);
            c.setMaxAge(60*60*24*30);
            resp.addCookie(c);
        }
        if(req.getParameter("user") != null)
        {
            User u = (User)req.getSession().getAttribute("user");
            u.getMarkedRecipe().add(RecipeRepository.find(id));
            UserRepository.edit(u);
        }**/
        p.println("OK");
    }
    
    private void removeMarked(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=utf-8");
        PrintWriter p = resp.getWriter();
        User u = (User)req.getSession().getAttribute("user");
        u.getMarkedRecipe().remove(RecipeRepository.find(Integer.parseInt(req.getParameter("id"))));
        UserRepository.edit(u);
    }

    
}
