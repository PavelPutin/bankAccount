package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.impl.CloseAccount;
import edu.vsu.putinpa.application.service.operation.info.ClosingAccountInfo;
import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@WebServlet("/close")
final public class CloseServlet extends HttpServlet {
    private AccountsService accountsService;
    private OperationsService operationsService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        accountsService = ((AnnotationContext) config.getServletContext().getAttribute("ComponentContext"))
                .getComponent("AccountsServiceImpl", AccountsService.class);
        operationsService = ((AnnotationContext) config.getServletContext().getAttribute("ComponentContext"))
                .getComponent("OperationsServiceImpl", OperationsService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client user = (Client) req.getSession().getAttribute("user");
        if (user == null) {
            req.setAttribute("errorMessage", "You aren't authorized");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }

        req.setAttribute("user", user);
        req.setAttribute("accounts", accountsService.getBy(user).stream()
                .filter(a -> a.getWhenClosed() == null)
                .toList());
        req.getRequestDispatcher("/closeOperation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client user = (Client) req.getSession().getAttribute("user");
        if (user == null) {
            req.setAttribute("errorMessage", "You aren't authorized");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }

        try {
            UUID targetUUID = UUID.fromString(req.getParameter("target"));
            Account target = accountsService.getBy(targetUUID).orElseThrow();

            UUID recipientUUID = UUID.fromString(req.getParameter("recipient"));
            Account recipient = accountsService.getBy(recipientUUID).orElseThrow();

            ClosingAccountInfo info = new ClosingAccountInfo(
                    user,
                    target,
                    recipient
            );
            operationsService.executeOperation(new CloseAccount(operationsService, info));
            resp.sendRedirect("/hello");
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage() + Arrays.toString(e.getStackTrace()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("user", user);
            req.setAttribute("accounts", accountsService.getBy(user).stream()
                    .filter(a -> a.getWhenClosed() == null)
                    .toList());
            req.getRequestDispatcher("close.jsp").forward(req, resp);
        }
    }
}
