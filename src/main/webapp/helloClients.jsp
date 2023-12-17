<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="edu.vsu.putinpa.application.service.ClientsService" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 17.12.2023
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private ClientsService clientsService;
    public void jspInit() {
        clientsService = ((AnnotationContext) getServletContext().getAttribute("ComponentContext"))
                .getComponent("ClientsServiceImpl", ClientsService.class);
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Все клиенты: <%=clientsService.getAll()%>
</body>
</html>
