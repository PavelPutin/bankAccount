<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Transfer</title>
</head>
<body>
<form action="transfer" method="post">
    <div>
        <%=response.getStatus()%>
        <%=(String) request.getAttribute("errorMessage")%>>
    </div>

    <div>
        <label for="sender">Выберите счёт для списания на созданый:</label>
        <select name="sender" id="sender">
            <%
                List<Account> senders = (List<Account>) request.getAttribute("senders");
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
                List<Account> recipient = (List<Account>) request.getAttribute("recipient");
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

    <input type="submit" value="Перевести">
</form>
</body>
</html>
