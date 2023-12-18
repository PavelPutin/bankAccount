<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="edu.vsu.putinpa.application.service.ClientsService" %>
<%@ page import="edu.vsu.putinpa.application.model.Client" %>
<%@ page import="edu.vsu.putinpa.application.service.ClientNotFoundException" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 17.12.2023
  Time: 21:10
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Client client = clientsService.getByName(username);
            if (!client.getPassword().equals(password)) {
                throw new ClientNotFoundException();
            }

            session.setAttribute("user", client);
            response.sendRedirect("/hello.jsp");

        } catch (ClientNotFoundException e) {
            errorMessage = "Can't login: invalid name or password";
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="login.jsp" method="post">
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
