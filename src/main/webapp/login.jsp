<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 17.12.2023
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="login" method="post">
        <div>
            <label for="username">Имя пользователя</label>
            <input type="text" name="username" id="username" required
                   value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>">
        </div>
        <div>
            <label for="password">Пароль</label>
            <input type="password" name="password" id="password" required
                   value="<%= request.getParameter("password") != null ? request.getParameter("password") : "" %>">
        </div>

        <div>
            <%=response.getStatus()%>
            <c:out value="${errorMessage}"/>
        </div>

        <input type="submit" value="Войти">
    </form>
</body>
</html>
