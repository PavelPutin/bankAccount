<%@ page import="edu.vsu.putinpa.application.service.AccountsService" %>
<%@ page import="edu.vsu.putinpa.application.service.OperationsService" %>
<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="edu.vsu.putinpa.application.model.Client" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.UUID" %>
<%@ page import="edu.vsu.putinpa.application.model.Money" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.info.ReplenishmentInfo" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.impl.Replenishment" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    private AccountsService accountsService;
    private OperationsService operationsService;
    public void jspInit() {
        accountsService = ((AnnotationContext) getServletContext().getAttribute("ComponentContext"))
                .getComponent("AccountsServiceImpl", AccountsService.class);
        operationsService = ((AnnotationContext) getServletContext().getAttribute("ComponentContext"))
                .getComponent("OperationsServiceImpl", OperationsService.class);
    }
%>
<%
    Client user = (Client) session.getAttribute("user");
    if (user == null) {
        request.setAttribute("errorMessage", "You aren't authorized");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);

    }
%>

<%
    if (request.getMethod().equals("POST")) {
        try {

            UUID recipientUUID = UUID.fromString(request.getParameter("recipient"));
            Account recipient = accountsService.getBy(recipientUUID).orElseThrow();
            double value = Double.parseDouble(request.getParameter("balance"));
            String currency = "ru";
            Money money = new Money(currency, BigDecimal.valueOf(value));

            ReplenishmentInfo info = new ReplenishmentInfo(
                    user,
                    recipient,
                    money
            );

            operationsService.executeOperation(new Replenishment(operationsService, info));

            response.sendRedirect("/hello.jsp");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage() + Arrays.toString(e.getStackTrace()));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
%>

<html>
<head>
    <title>Replenish</title>
</head>
<body>
<form action="replenishOperation.jsp" method="post">
    <div>
        <%=response.getStatus()%>
        <c:out value="${errorMessage}"/>
    </div>
    <div>
        <label for="recipient">Выберите счёт для пополнения:</label>
        <select name="recipient" id="recipient" required>
            <%
                List<Account> accounts = accountsService.getBy(user);
                pageContext.setAttribute("accounts", accounts);
            %>
            <c:forEach var="account" items="${accounts}">
                <option value="${account.getUuid()}">
                    <c:out value="${account.getUuid()}"/> <c:out value="${account.getName()}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <label for="balance">Сумма пополнения: </label>
        <input type="text" id="balance" name="balance" required/>
    </div>

    <input type="submit" value="Пополнить счёт">
</form>
</body>
</html>
