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
import models.User;
import providers.EncryptionProvider;
import repositories.UserRepository;

/**
 *
 * @author MaliszewskiDorian
 */
@WebServlet(name = "Login", urlPatterns = {"/login","/logout"})
public class LoginServlet extends HttpServlet {

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
        System.out.println("LoginController");       
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
        System.out.println("Get method");
        response.setContentType("text/html;charset=UTF-8");
        String url = "/login/login.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
        request.getSession().setAttribute("success", null);
        request.getSession().setAttribute("message", null);
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
        System.out.println("POST method");
        User user = null;
        String pass = "";
        try{
            user = UserRepository.findByUsername(request.getParameter("username"));
            if(request.getParameter("password") == null){
                throw new NullPointerException("Le paramètre password n'est pas renseigné");
            }
            pass = request.getParameter("password");
            
        }catch(NullPointerException e){
            e.printStackTrace();
            System.out.println("Paramètres non renseignés");
        }
        if(user == null){
            System.out.println("User not find");
            response.setContentType("text/html;charset=UTF-8");
            String url = "/login/login.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            request.getSession().setAttribute("success", false);
            request.getSession().setAttribute("message", "Identifiant incorrect");
            response.sendRedirect(request.getContextPath() + request.getServletPath()); 
            return;
        }
        
        if(EncryptionProvider.verifyPassword(user.getPassword(), pass, user.getSalt())){
            request.getSession().setAttribute("user", user);
            response.sendRedirect(getServletContext().getContextPath());
        }
        else
        {
            System.out.println("Les identifiants ne correspondent pas");
            request.getSession().setAttribute("success", false);
            request.getSession().setAttribute("message", "Identifiant incorrect");
            response.sendRedirect(request.getContextPath() + request.getServletPath());           
        }
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
        if(req.getServletPath().equals("/logout")){
            this.logout(req,resp);
        }else{
           super.service(req, resp);
        }
        return;
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       req.getSession().invalidate();
       resp.sendRedirect(req.getContextPath());
    }
    
    
}
