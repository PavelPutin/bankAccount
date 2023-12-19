<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="edu.vsu.putinpa.application.model.Client" %>
<%@ page import="edu.vsu.putinpa.application.service.AccountsService" %>
<%@ page import="edu.vsu.putinpa.infrastructure.di.AnnotationContext" %>
<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.vsu.putinpa.application.model.JournalOperation" %>
<%@ page import="edu.vsu.putinpa.application.service.OperationsHistoryService" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 18.12.2023
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private AccountsService accountsService;
    private OperationsHistoryService operationsHistoryService;
    public void jspInit() {
        accountsService = ((AnnotationContext) getServletContext().getAttribute("ComponentContext"))
                .getComponent("AccountsServiceImpl", AccountsService.class);
        operationsHistoryService = ((AnnotationContext) getServletContext().getAttribute("ComponentContext"))
                .getComponent("OperationsHistoryServiceImpl", OperationsHistoryService.class);
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
            pageContext.setAttribute("accounts", accounts);
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

    <div>
        <h2>История операций</h2>
        <%
            List<JournalOperation> journalOperations = operationsHistoryService.getAllByClient(user);
            pageContext.setAttribute("history", journalOperations);
        %>
        <c:if test="${history.isEmpty()}">
            <p><c:out value="Вы не сделали ни одной операции"/></p>
        </c:if>
        <c:if test="${!history.isEmpty()}">
            <table>
                <thead>
                <tr>
                    <th>uuid</th>
                    <th>client</th>
                    <th>sender</th>
                    <th>recipient</th>
                    <th>type</th>
                    <th>money</th>
                    <th>currency</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="operation" items="${history}">
                    <tr>
                        <td><c:out value="${operation.getUuid()}"/></td>
                        <td><c:out value="${operation.getClient().getUuid()}"/></td>

                        <c:if test="${operation.getSender() != null}">
                            <td><c:out value="${operation.getSender().getUuid()}"/></td>
                        </c:if>
                        <c:if test="${operation.getSender() == null}">
                            <td>-</td>
                        </c:if>

                        <c:if test="${operation.getRecipient() != null}">
                            <td><c:out value="${operation.getRecipient().getUuid()}"/></td>
                        </c:if>
                        <c:if test="${operation.getRecipient() == null}">
                            <td>-</td>
                        </c:if>

                        <td><c:out value="${operation.getType()}"/></td>
                        <td><c:out value="${operation.getMoney().value()}"/></td>
                        <td><c:out value="${operation.getMoney().currency()}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</c:if>
</body>
</html>
