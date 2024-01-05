package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.impl.Replenishment;
import edu.vsu.putinpa.application.service.operation.info.ReplenishmentInfo;
import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@WebServlet("/replenish")
public class ReplenishServlet extends HttpServlet {
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
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
        
        req.setAttribute("accounts", accountsService.getBy(user));
        req.getRequestDispatcher("replenishOperation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client user = (Client) req.getSession().getAttribute("user");
        if (user == null) {
            req.setAttribute("errorMessage", "You aren't authorized");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
        try {
            UUID recipientUUID = UUID.fromString(req.getParameter("recipient"));
            Account recipient = accountsService.getBy(recipientUUID).orElseThrow();
            double value = Double.parseDouble(req.getParameter("balance"));
            String currency = "ru";
            Money money = new Money(currency, BigDecimal.valueOf(value));

            ReplenishmentInfo info = new ReplenishmentInfo(
                    user,
                    recipient,
                    money
            );

            operationsService.executeOperation(new Replenishment(operationsService, info));

            resp.sendRedirect("/hello");
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage() + Arrays.toString(e.getStackTrace()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("accounts", accountsService.getBy(user));
            req.getRequestDispatcher("replenishOperation.jsp");
        }
    }
}
