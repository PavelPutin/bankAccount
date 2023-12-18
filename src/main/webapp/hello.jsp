<%@ page import="edu.vsu.putinpa.application.model.Client" %>
<%@ page import="edu.vsu.putinpa.application.service.AccountsService" %>
<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 18.12.2023
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private AccountsService accountsService;
    public void jspInit() {
        accountsService = ((AnnotationContext) getServletContext().getAttribute("ComponentContext"))
                .getComponent("AccountsServiceImpl", AccountsService.class);
    }
%>
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
        out.println("<a href=\"login.jsp\">Войти</a>");
    }
%>

<%
    if (user != null) {
        out.println("<a href=\"logout\"> Выйти</a>");
    }
%>

<%
    if (user != null) {
        out.println("<h2>Ваши счета:</h2>");
        List<Account> accounts = accountsService.getBy(user);
        if (accounts.isEmpty()) {
            out.println("У вас пока не открыто ни одного счёта");
        } else {
            out.println("<table><thead>");
            out.println("<tr><th>uuid</th><th>name</th><th>created</th><th>closed</th><th>balance</th></tr>");
            out.println("</thead><tbody>");
            for (Account account : accounts) {
                out.println("<tr>" +
                            "<td>" + account.getUuid() + "</td>" +
                            "<td>" + account.getName() + "</td>" +
                            "<td>" + account.getWhenOpened() + "</td>" +
                            "<td>" + account.getWhenClosed() + "</td>" +
                            "<td>" + account.getBalance() + "</td>" +
                            "</tr>");
            }

            out.println("</tbody></table>");
        }
    }
%>

</body>
</html>
