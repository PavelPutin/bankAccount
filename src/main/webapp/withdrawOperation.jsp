<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Withdraw</title>
</head>
<body>
<form action="withdraw" method="post">
    <div>
        <%=response.getStatus()%>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
        %>
        <%=errorMessage == null ? errorMessage : ""%>>
    </div>
    <div>
        <label for="sender">Выберите счёт для списания:</label>
        <select name="sender" id="sender" required>
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
        <label for="balance">Сумма снятия: </label>
        <input type="text" id="balance" name="balance" required/>
    </div>

    <input type="submit" value="Снять со счёта">
</form>
</body>
</html>
