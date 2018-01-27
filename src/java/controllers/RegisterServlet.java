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
 * @author dorian
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

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
        System.out.println("RegisterController");
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
        System.out.println("GET method");
        /*try {
            request.getSession().setAttribute("token", MessageDigest.getInstance("MD5").digest("token".getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        response.setContentType("text/html;charset=UTF-8");
        String url = "/login/register.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
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
        System.out.println("Post method");
        User u = new User();
        try{
            u.setUsername(request.getParameter("username"));
            String pass = request.getParameter("password");
            u.setFirstName(request.getParameter("firstName"));
            u.setLastName(request.getParameter("lastName"));
            u.setEmail(request.getParameter("email"));
            u.setPostalCode(request.getParameter("postalCode"));
            //String token = (String)request.getSession().getAttribute("token");
            String[] saltAndPassEncrypted = EncryptionProvider.encrypt(pass);
            u.setPassword(saltAndPassEncrypted[1]);
            u.setSalt(saltAndPassEncrypted[0]);
            UserRepository.add(u);
            request.getSession().setAttribute("user", u);
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        catch(Exception e){
            System.out.println("Une erreur est survenue");
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath());
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

}
