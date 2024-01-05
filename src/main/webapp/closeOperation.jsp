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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Close account</title>
</head>
<body>

<form action="close" method="post">
    <div>
        <%=response.getStatus()%>
        <c:out value="${errorMessage}"/>
    </div>
    <%
        List<Account> accounts = (List<Account>) request.getAttribute("accounts");
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
