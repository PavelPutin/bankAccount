<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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

<c:if test="${user == null}">
    <a href="registration.jsp">Зарегистрироваться</a>
    <a href="login.jsp">Войти</a>
</c:if>

<c:if test="${user != null}">
    <a href="logout"> Выйти</a>
    <div>
        <h2>Операции со счетами</h2>
        <ul>
            <li><a href="openOperation.jsp">Открыть новый счёт</a></li>
            <li><a href="closeOperation.jsp">Закрыть счёт</a></li>
            <li><a href="replenishOperation.jsp">Пополнить счёт</a></li>
            <li><a href="withdrawOperation.jsp">Снять со счёта</a></li>
            <li><a href="transferOperation.jsp">Перевод между счетами</a></li>
        </ul>
    </div>
    <div>
        <h2>Ваши счета:</h2>
        <%
            List<Account> accounts = accountsService.getBy(user);
            request.setAttribute("accounts", accounts);
        %>
        <c:if test="${accounts.isEmpty()}">
            <p><c:out value="У вас пока не открыто ни одного счёта"/></p>
        </c:if>
        <c:if test="${!accounts.isEmpty()}">
            <table>
                <thead>
                    <tr>
                        <th>uuid</th>
                        <th>name</th>
                        <th>created</th>
                        <th>closed</th>
                        <th>balance</th>
                        <th>currency</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="account" items="${accounts}">
                    <tr>
                        <td><c:out value="${account.getUuid()}"/></td>
                        <td><c:out value="${account.getName()}"/></td>
                        <td><c:out value="${account.getWhenOpened()}"/></td>
                        <td><c:out value="${account.getWhenClosed()}"/></td>
                        <td><c:out value="${account.getBalance().value()}"/></td>
                        <td><c:out value="${account.getBalance().currency()}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</c:if>
</body>
</html>
