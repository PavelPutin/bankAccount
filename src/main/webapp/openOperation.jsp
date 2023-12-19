<%@ page import="edu.vsu.putinpa.application.model.Client" %>
<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.vsu.putinpa.application.service.AccountsService" %>
<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.info.OpeningAccountInfo" %>
<%@ page import="edu.vsu.putinpa.application.model.Money" %>
<%@ page import="edu.vsu.putinpa.application.service.OperationsService" %>
<%@ page import="edu.vsu.putinpa.application.service.operation.impl.OpenAccount" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.rmi.RemoteException" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:55
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
            String name = request.getParameter("name");
            boolean replenish = request.getParameter("replenish").equals("on");

            Account sender = null;
            Money money = null;

            if (replenish) {
                UUID senderUUID = UUID.fromString(request.getParameter("sender"));
                sender = accountsService.getBy(senderUUID).orElseThrow();

                double value = Double.parseDouble(request.getParameter("balance"));
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
            response.sendRedirect("/hello.jsp");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage() + Arrays.toString(e.getStackTrace()));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
%>
<html>
<head>
    <title>Open new account</title>
</head>
<body>
<form action="openOperation.jsp" method="post">
    <div>
        <%=response.getStatus()%>
        <c:out value="${errorMessage}"/>
    </div>
    <div>
        <label for="name">Название счёта</label>
        <input type="text" name="name" id="name" required>
    </div>
    <div>
        <label for="replenish">Пополнить счёт: </label>
        <input type="checkbox" name="replenish" id="replenish" onclick="toggleSelect()">
    </div>
    <div>
        <label for="sender">Выберите счёт для списания на созданый:</label>
        <select name="sender" id="sender" disabled>
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
        <input type="text" id="balance" name="balance" disabled/>
    </div>

    <input type="submit" value="Создать счёт">
</form>

<script>
    function toggleSelect() {
        const replenishCheckbox = document.getElementById("replenish");
        const senderSelect = document.getElementById("sender");
        const balanceInput = document.getElementById("balance");


        if (replenishCheckbox.checked) {
            senderSelect.disabled = false;
            balanceInput.disabled = false;
        } else {
            senderSelect.disabled = true;
            balanceInput.disabled = true;
        }
    }
</script>
</body>
</html>
