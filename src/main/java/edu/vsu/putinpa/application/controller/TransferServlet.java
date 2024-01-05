package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.impl.Transfer;
import edu.vsu.putinpa.application.service.operation.info.TransferInfo;
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

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
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

        req.setAttribute("senders", accountsService.getBy(user).stream().filter(a -> a.getWhenClosed() == null).toList());
        req.setAttribute("recipient", accountsService.getAll().stream().filter(a -> a.getWhenClosed() == null).toList());
        req.getRequestDispatcher("transferOperation.jsp").forward(req, resp);
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
            UUID senderUUID = UUID.fromString(req.getParameter("sender"));
            Account sender = accountsService.getBy(senderUUID).orElseThrow();

            UUID recipientUUID = UUID.fromString(req.getParameter("recipient"));
            Account recipient = accountsService.getBy(recipientUUID).orElseThrow();

            double value = Double.parseDouble(req.getParameter("balance"));
            Money money = new Money("ru", BigDecimal.valueOf(value));

            TransferInfo info = new TransferInfo(
                    user,
                    sender,
                    recipient,
                    money
            );
            operationsService.executeOperation(new Transfer(operationsService, info));
            resp.sendRedirect("/hello");
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage() + Arrays.toString(e.getStackTrace()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("senders", accountsService.getBy(user).stream().filter(a -> a.getWhenClosed() == null).toList());
            req.setAttribute("recipient", accountsService.getAll().stream().filter(a -> a.getWhenClosed() == null).toList());
            req.getRequestDispatcher("transferOperation.jsp").forward(req, resp);
        }
    }
}
