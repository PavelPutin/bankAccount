<%@ page import="edu.vsu.putinpa.application.service.OperationsService" %>
<%@ page import="edu.vsu.putinpa.application.service.AccountsService" %>
<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="edu.vsu.putinpa.application.model.Client" %>
<%@ page import="java.util.UUID" %>
<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.info.TransferInfo" %>
<%@ page import="edu.vsu.putinpa.application.model.Money" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.impl.Transfer" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
            UUID senderUUID = UUID.fromString(request.getParameter("sender"));
            Account sender = accountsService.getBy(senderUUID).orElseThrow();

            UUID recipientUUID = UUID.fromString(request.getParameter("recipient"));
            Account recipient = accountsService.getBy(recipientUUID).orElseThrow();

            double value = Double.parseDouble(request.getParameter("balance"));
            Money money = new Money("ru", BigDecimal.valueOf(value));

            TransferInfo info = new TransferInfo(
                    user,
                    sender,
                    recipient,
                    money
            );
            operationsService.executeOperation(new Transfer(operationsService, info));
            response.sendRedirect("/hello.jsp");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage() + Arrays.toString(e.getStackTrace()));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
%>
<html>
<head>
    <title>Transfer</title>
</head>
<body>
<form action="transferOperation.jsp" method="post">
    <div>
        <%=response.getStatus()%>
        <c:out value="${errorMessage}"/>
    </div>

    <div>
        <label for="sender">Выберите счёт для списания на созданый:</label>
        <select name="sender" id="sender">
            <%
                List<Account> senders = accountsService.getBy(user).stream().filter(a -> a.getWhenClosed() == null).toList();
                pageContext.setAttribute("senders", senders);
            %>
            <c:forEach var="account" items="${senders}">
                <option value="${account.getUuid()}">
                    <c:out value="${account.getUuid()}"/> <c:out value="${account.getName()}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <label for="recipient">Выберите счёт для списания на созданый:</label>
        <select name="recipient" id="recipient">
            <%
                List<Account> recipient = accountsService.getAll().stream().filter(a -> a.getWhenClosed() == null).toList();
                pageContext.setAttribute("recipient", recipient);
            %>
            <c:forEach var="account" items="${recipient}">
                <option value="${account.getUuid()}">
                    <c:out value="${account.getUuid()}"/> <c:out value="${account.getName()}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <label for="balance">Сумма пополнения: </label>
        <input type="text" id="balance" name="balance"/>
    </div>

    <input type="submit" value="Создать счёт">
</form>
</body>
</html>
