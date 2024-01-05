package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.impl.OpenAccount;
import edu.vsu.putinpa.application.service.operation.info.OpeningAccountInfo;
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

@WebServlet("/open")
public class OpenServlet extends HttpServlet {
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
        req.getRequestDispatcher("openOperation.jsp").forward(req, resp);
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
            String name = req.getParameter("name");
            boolean replenish = req.getParameter("replenish") != null;

            Account sender = null;
            Money money = null;

            if (replenish) {
                UUID senderUUID = UUID.fromString(req.getParameter("sender"));
                sender = accountsService.getBy(senderUUID).orElseThrow();

                double value = Double.parseDouble(req.getParameter("balance"));
                money = new Money("ru", BigDecimal.valueOf(value));
            }

            OpeningAccountInfo openingAccountInfo = new OpeningAccountInfo(
                    user,
                    name,
                    "ru",
                    sender,
                    money
            );
            operationsService.executeOperation(new OpenAccount(operationsService, openingAccountInfo));
            resp.sendRedirect("/hello");
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage() + Arrays.toString(e.getStackTrace()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("accounts", accountsService.getBy(user));
            req.getRequestDispatcher("openOperations.jsp").forward(req, resp);
        }
    }
}
