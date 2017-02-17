package io.muic.ooc.webapp;

import io.muic.ooc.webapp.service.MySQLService;
import io.muic.ooc.webapp.service.SecurityService;
import java.io.File;
import javax.servlet.ServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Created by gigadot on 02-Feb-17.
 */
public class Webapp {
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/test";

    public static void main(String[] args) {

        String docBase = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        SecurityService securityService = new SecurityService();

        MySQLService mySQLService = new MySQLService(MYSQL_DRIVER,MYSQL_URL);

        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);
        servletRouter.setMySQLService(mySQLService);



        Context ctx;
        try {
            ctx = tomcat.addWebapp("/", new File(docBase).getAbsolutePath());
            servletRouter.init(ctx);
            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException | LifecycleException ex) {
            ex.printStackTrace();
        }

    }


}
