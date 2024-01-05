<%@ page import="edu.vsu.putinpa.application.model.Account" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: RobotComp.ru
  Date: 19.12.2023
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Open new account</title>
</head>
<body>
<form action="open" method="post">
    <div>
        <%=response.getStatus()%>
        <c:out value="${errorMessage}"/>
    </div>
    <div>
        <label for="name">Название счёта</label>
        <input type="text" name="name" id="name" required>
    </div>
    <div>
        <label for="replenish">Пополнить счёт: </label>
        <input type="checkbox" name="replenish" id="replenish" onclick="toggleSelect()">
    </div>
    <div>
        <label for="sender">Выберите счёт для списания на созданый:</label>
        <select name="sender" id="sender" disabled>
            <%
                List<Account> accounts = (List<Account>) request.getAttribute("accounts");
            %>
            <c:forEach var="account" items="${accounts}">
                <option value="${account.getUuid()}">
                    <c:out value="${account.getUuid()}"/> <c:out value="${account.getName()}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <label for="balance">Сумма пополнения: </label>
        <input type="text" id="balance" name="balance" disabled/>
    </div>

    <input type="submit" value="Создать счёт">
</form>

<script>
    function toggleSelect() {
        const replenishCheckbox = document.getElementById("replenish");
        const senderSelect = document.getElementById("sender");
        const balanceInput = document.getElementById("balance");


        if (replenishCheckbox.checked) {
            senderSelect.disabled = false;
            balanceInput.disabled = false;
        } else {
            senderSelect.disabled = true;
            balanceInput.disabled = true;
        }
    }
</script>
</body>
</html>
