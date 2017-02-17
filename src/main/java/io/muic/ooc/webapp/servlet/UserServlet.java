/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.MySQLService;
import io.muic.ooc.webapp.service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UserServlet extends HttpServlet {
    private SecurityService securityService;
    private MySQLService mySQLService;
    public void setSecurityManager(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setMySQLManager(MySQLService mySQLService){this.mySQLService = mySQLService;}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            String username = (String) request.getSession().getAttribute("username");
            String firstName = mySQLService.getFirstName(username);
            request.setAttribute("username", username);
            request.setAttribute("firstname",firstName);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/user.jsp");
            rd.include(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}
