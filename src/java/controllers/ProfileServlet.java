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
 * Servlet du profile
 * @author Dorian Maliszewski
 */
@WebServlet(name = "Profile", urlPatterns = {"/profile/*", "/myrecipes"})
public class ProfileServlet extends HttpServlet {

    /**
     * Retourne la page de profil
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "/profil/index.jsp";
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
     * Vérifie que l'utilisateur est bien connecté sinon renvoie vers la page de login
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
        if(req.getServletPath().equals("/myrecipes")){
            getMyRecipe(req,resp);
        }else if (req.getPathInfo() != null && req.getPathInfo().equals("/edit")){
            edit(req,resp);
        }else{
            super.service(req, resp); //To change body of generated methods, choose Tools | Templates.   
        }
    }

    private void getMyRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "/profil/myrecipe.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
        request.getSession().setAttribute("message", null);
        request.getSession().setAttribute("success", null);
    }

    /**
     * Modifie l'utilisateur avec les paramètres renseigné, si rien n'est renseigné pour le paramètre password alors ne modifie pas le mot de passe
     * @param request La requete
     * @param response La réponse
     * @throws ServletException
     * @throws IOException 
     */
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getMethod().equals("POST")){
            User user = (User)request.getSession().getAttribute("user");
            try{
                User u = UserRepository.find(user.getId());
                u.setEmail(request.getParameter("email"));
                u.setFirstName(request.getParameter("firstName"));
                u.setLastName(request.getParameter("lastName"));
                u.setPostalCode(request.getParameter("postalCode"));
                
                System.out.println("controllers.ProfileServlet.edit() : password : count :" + request.getParameter("password") + " : " + request.getParameter("password").length());
                //Si il a entrer un nouveau mot de passe alors on met à jour
                if(request.getParameter("password").length() != 0){
                    String[] array = EncryptionProvider.encrypt(request.getParameter("password"));
                    u.setPassword(array[1]);
                    u.setSalt(array[0]);
                }
                
                //On met à jour l'utilisateur
                UserRepository.edit(u);
                request.setAttribute("user", u);
                
                //FlashBag success
                request.getSession().setAttribute("message", "Modification effectuées");
                request.getSession().setAttribute("success", true);
                
                response.sendRedirect(request.getContextPath() + request.getServletPath());
                
            //En cas d'erreur un flashbag qui précise le type d'erreur : client ou serveur
            }catch(NullPointerException e){
                e.printStackTrace();
                request.getSession().setAttribute("message", "Tous les paramètres ne sont pas renseignés");
                request.getSession().setAttribute("success", false);
                response.sendRedirect(request.getContextPath() + request.getServletPath() + "/edit");
            }catch(Exception e){
                e.printStackTrace();
                request.getSession().setAttribute("message", "Erreur lors de la modification, veuillez réessayez");
                request.getSession().setAttribute("success", false);
                response.sendRedirect(request.getContextPath() + request.getServletPath() + "/edit");
            }
        }
        else{
            response.setContentType("text/html;charset=UTF-8");
            String url = "/profil/form.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
            request.getSession().setAttribute("message", null);
            request.getSession().setAttribute("success", null);
        }
    }

    
}
