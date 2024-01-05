<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="edu.vsu.putinpa.application.model.Client" %>
<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.vsu.putinpa.application.model.JournalOperation" %><%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 18.12.2023
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Client user = (Client) request.getAttribute("user");
%>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<h1><%= user == null ? "You have not logged in" : "You have logged in, " + user.getName() %></h1>

<c:if test="${user == null}">
    <a href="registration">Зарегистрироваться</a>
    <a href="login">Войти</a>
</c:if>

<c:if test="${user != null}">
    <form action="logout" method="post">
        <button type="submit">Выйти</button>
    </form>
    <div>
        <h2>Операции со счетами</h2>
        <ul>
            <li><a href="open">Открыть новый счёт</a></li>
            <li><a href="close">Закрыть счёт</a></li>
            <li><a href="replenish">Пополнить счёт</a></li>
            <li><a href="withdraw">Снять со счёта</a></li>
            <li><a href="transfer">Перевод между счетами</a></li>
        </ul>
    </div>
    <div>
        <h2>Ваши счета:</h2>
        <%
            List<Account> accounts = (List<Account>) request.getAttribute("accounts");
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
            List<JournalOperation> journalOperations = (List<JournalOperation>) request.getAttribute("history");
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
