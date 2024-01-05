<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Replenish</title>
</head>
<body>
<form action="replenish" method="post">
    <div>
        <%=response.getStatus()%>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
        %>
        <%=errorMessage == null ? errorMessage : ""%>>
    </div>
    <div>
        <label for="recipient">Выберите счёт для пополнения:</label>
        <select name="recipient" id="recipient" required>
            <%
                List<Account> accounts = (List<Account>) request.getAttribute("accounts");
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
