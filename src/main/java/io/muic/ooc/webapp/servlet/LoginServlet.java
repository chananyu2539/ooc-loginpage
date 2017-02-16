/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.MySQLService;
//import io.muic.ooc.webapp.service.SecurityService;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gigadot
 */
public class LoginServlet extends HttpServlet {

//    private SecurityService securityService;
    private MySQLService mySQLService;

    public void setMySQLManager(MySQLService mySQLManager) {
        this.mySQLService = mySQLManager;
    }

//    public void setSecurityManager(SecurityService securityService) {
//        this.securityService = securityService;
//    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // do login post logic
        String username = request.getParameter("username");
        String password = request.getParameter("password");



        if(!username.equals("") && !password.equals("")){
            // authenticate

            String[] inp = {username,password};
            try {
                if (mySQLService.isValidInput(inp)) {
                    request.getSession().setAttribute("username", username);
                    response.sendRedirect("/");
//                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
//                rd.include(request, response);
                } else {
                    String failError = "Wrong username or password.";
                    request.setAttribute("error", failError);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                    rd.include(request, response);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            String nullError = "Username or Password is missing.";
            request.setAttribute("error",nullError);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
            rd.include(request, response);
        }
        // extract username and password from request
        // check username and password against database
        // if valid then set username attribute to session via securityService
        // else put error message to render error on the login form
//        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
//        rd.include(request, response);
    }


}
