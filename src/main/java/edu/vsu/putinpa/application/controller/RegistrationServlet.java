package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.service.ClientsService;
import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private ClientsService clientsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        clientsService = ((AnnotationContext) config.getServletContext().getAttribute("ComponentContext"))
                .getComponent("ClientsServiceImpl", ClientsService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Client client = clientsService.register(name, password);
            req.getSession().setAttribute("user", client);
            resp.sendRedirect("/hello");
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        }
    }
}
