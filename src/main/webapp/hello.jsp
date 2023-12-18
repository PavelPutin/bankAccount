<%@ page import="edu.vsu.putinpa.application.model.Client" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 18.12.2023
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Hello</title>
</head>
<body>
<%
    Client user = (Client) session.getAttribute("user");
    String message = user == null ? "You have not logged in" : "You have logged in, " + user.getName();
%>
<h1><%= message %></h1>
<%
    if (user == null) {
        out.println("<a href=\"/login.jsp\">Войти</a>");
    }
%>

</body>
</html>
