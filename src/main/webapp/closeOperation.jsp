<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.vsu.putinpa.application.service.AccountsService" %>
<%@ page import="edu.vsu.putinpa.application.service.OperationsService" %>
<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="edu.vsu.putinpa.application.model.Client" %>
<%@ page import="edu.vsu.putinpa.application.model.Money" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.info.OpeningAccountInfo" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.info.ClosingAccountInfo" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.impl.CloseAccount" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
            UUID targetUUID = UUID.fromString(request.getParameter("target"));
            Account target = accountsService.getBy(targetUUID).orElseThrow();

            UUID recipientUUID = UUID.fromString(request.getParameter("recipient"));
            Account recipient = accountsService.getBy(recipientUUID).orElseThrow();

            ClosingAccountInfo info = new ClosingAccountInfo(
                    user,
                    target,
                    recipient
            );
            operationsService.executeOperation(new CloseAccount(operationsService, info));
            response.sendRedirect("/hello.jsp");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage() + Arrays.toString(e.getStackTrace()));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
%>
<html>
<head>
    <title>Close account</title>
</head>
<body>

<form action="closeOperation.jsp" method="post">
    <div>
        <%=response.getStatus()%>
        <c:out value="${errorMessage}"/>
    </div>
    <%
        List<Account> accounts = accountsService.getBy(user).stream().filter(a -> a.getWhenClosed() == null).toList();
        pageContext.setAttribute("accounts", accounts);
    %>
    <div>
        <label for="target">Выберите счёт для закрытия:</label>
        <select name="target" id="target">

            <c:forEach var="targets" items="${accounts}">
                <option value="${targets.getUuid()}">
                    <c:out value="${targets.getUuid()}"/> <c:out value="${targets.getName()}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <label for="recipient">Выберите счёт, на который будут переведены деньги:</label>
        <select name="recipient" id="recipient">

            <c:forEach var="account" items="${accounts}">
                <option value="${account.getUuid()}">
                    <c:out value="${account.getUuid()}"/> <c:out value="${account.getName()}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <input type="submit" value="Закрыть счёт">
</form>

</body>
</html>
