<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr>
            <td>${meal.stringDateTime}</td>
            <td>${meal.description}</td>
            <c:if test="${meal.excess == true}">
                <td style="color: red">
                        ${meal.calories}
                </td>
            </c:if>
            <c:if test="${meal.excess != true}">
                <td style="color: green">
                        ${meal.calories}
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>