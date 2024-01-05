<%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Регистрация</h1>
<form action="registration" method="post">
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
        <%=request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
    </div>

    <input type="submit" value="Войти">
</form>
</body>
</html>
