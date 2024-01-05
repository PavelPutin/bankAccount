package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "HelloServlet", urlPatterns = "/hello")
final public class HelloServlet extends HttpServlet {
    private AccountsService accountsService;
    private OperationsHistoryService operationsHistoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        accountsService = ((AnnotationContext) config.getServletContext().getAttribute("ComponentContext"))
                .getComponent("AccountsServiceImpl", AccountsService.class);
        operationsHistoryService = ((AnnotationContext) config.getServletContext().getAttribute("ComponentContext"))
                .getComponent("OperationsHistoryServiceImpl", OperationsHistoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client user = (Client) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        if (user != null) {
            req.setAttribute("accounts", accountsService.getBy(user));
            req.setAttribute("history", operationsHistoryService.getAllByClient(user));
        }
        req.getRequestDispatcher("/hello.jsp").forward(req, resp);
    }
}
