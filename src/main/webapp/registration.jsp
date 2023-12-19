<%@ page import="edu.vsu.putinpa.application.service.ClientsService" %>
<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="edu.vsu.putinpa.application.model.Client" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    private ClientsService clientsService;
    private String errorMessage;
    public void jspInit() {
        clientsService = ((AnnotationContext) getServletContext().getAttribute("ComponentContext"))
                .getComponent("ClientsServiceImpl", ClientsService.class);
    }
%>

<%
    if (request.getMethod().equals("POST")) {
        String name = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Client client = clientsService.register(name, password);
            session.setAttribute("user", client);
            response.sendRedirect("/hello.jsp");
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }
%>
<html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Регистрация</h1>
<form action="registration.jsp" method="post">
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
        <%= errorMessage != null ? errorMessage : ""%>
    </div>

    <input type="submit" value="Войти">
</form>
</body>
</html>
