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
        response.setContentType("text/html;charset=UTF-8");
        String url = "/login/register.jsp";
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
        System.out.println("Post method");
        User u = new User();
        try{
            u.setUsername(request.getParameter("username"));
            String pass = request.getParameter("password");
            if(!pass.equals(request.getParameter("confirmPassword"))){
                request.getSession().setAttribute("message", "Les mots de passe ne correpsondent pas");
                request.getSession().setAttribute("success", false);
                processRequest(request, response);
                return;
            }
            u.setFirstName(request.getParameter("firstName"));
            u.setLastName(request.getParameter("lastName"));
            u.setEmail(request.getParameter("email"));
            u.setPostalCode(request.getParameter("postalCode"));
            String[] saltAndPassEncrypted = EncryptionProvider.encrypt(pass);
            u.setPassword(saltAndPassEncrypted[1]);
            u.setSalt(saltAndPassEncrypted[0]);
            UserRepository.add(u);
            request.getSession().setAttribute("user", u);
        }
        catch(NullPointerException | IOException | ServletException e){
            System.out.println("Une erreur est survenue");
            request.getSession().setAttribute("message", "Une etteur est survenue lors de l'enregistrement. Veuillez réessayer");
            request.getSession().setAttribute("success", false);
            e.printStackTrace();
            processRequest(request, response);
            return;
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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        super.service(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    
}
