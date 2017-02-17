package io.muic.ooc.webapp.servlet;

import com.ja.security.PasswordHash;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.muic.ooc.webapp.service.MySQLService;
import io.muic.ooc.webapp.service.SecurityService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chananyu2539 on 2/15/2017 AD.
 */
public class RegisterServlet extends HttpServlet {

    private SecurityService securityService;
    private MySQLService mySQLService;

    public void setMySQLManager(MySQLService mySQLManager) {
        this.mySQLService = mySQLManager;
    }

    public void setSecurityManager(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String repassword = request.getParameter("repassword");
        String password = request.getParameter("password");
        String name = request.getParameter("name");


        if (!username.equals("") && !password.equals("") && !repassword.equals("") && !name.equals(""))  {
            // authenticate
            if(username.length()>12){
                String userError = "Username length is greater than 12.";
                request.setAttribute("error", userError);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
                rd.include(request, response);
            }
            else if(password.equals(repassword)) {
                try {
                    String hashPass = new PasswordHash().createHash(password);
                    mySQLService.writeData(username, hashPass, name);
                    request.getSession().setAttribute("username", username);
                    response.sendRedirect("/");
                }catch (MySQLIntegrityConstraintViolationException e){
                    String duplicateError = "That username is already taken.";
                    request.setAttribute("error", duplicateError);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
                    rd.include(request, response);
                }
                catch (Exception e){
                    System.out.println("Shouldn't reach here");
                    e.printStackTrace();
                }
            }
            else {
                String passError = "Password doesn't match each other.";
                request.setAttribute("error", passError);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
                rd.include(request, response);
            }
        } else {
            String nullError = "Please fill in all the block.";
            request.setAttribute("error", nullError);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
            rd.include(request, response);
        }


    }
}
