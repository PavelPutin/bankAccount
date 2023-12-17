package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.ApplicationContextListener;
import edu.vsu.putinpa.application.service.ClientsService;
import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "IndexServlet", value = "/index")
public class IndexServlet extends HttpServlet {
    private ClientsService clientsService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        clientsService = ((AnnotationContext) config.getServletContext().getAttribute("ComponentContext"))
                .getComponent("ClientsService", ClientsService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("Hello");
        out.println(clientsService.getAll().get(0).getName());
        out.println("</body></html>");
    }
}
