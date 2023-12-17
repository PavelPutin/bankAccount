package edu.vsu.putinpa.application;

import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        try {
            AnnotationContext context = new AnnotationContext("edu.vsu.putinpa");
            servletContext.setAttribute("ComponentContext", context);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
